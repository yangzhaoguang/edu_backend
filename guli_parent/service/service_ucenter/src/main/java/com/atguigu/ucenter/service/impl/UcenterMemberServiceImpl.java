package com.atguigu.ucenter.service.impl;

import com.atguigu.commonutils.HttpClientUtils;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.ucenter.entity.RegisterVo;
import com.atguigu.ucenter.entity.WXConstantPropertiesUtil;
import com.atguigu.ucenter.mapper.UcenterMemberMapper;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ucenter.entity.UcenterMember;
import com.google.gson.Gson;
import org.bouncycastle.asn1.cms.OtherRecipientInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author 杨照光
 * @description 针对表【ucenter_member(会员表)】的数据库操作Service实现
 * @createDate 2022-09-04 11:36:08
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember>
        implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description 登录
     * @date 2022/9/4 11:57
     * @param member
     * @return java.lang.String
     */
    @Override
    public String login(UcenterMember member) throws Exception{
        // 校验信息
        String password = member.getPassword();
        String phone = member.getMobile();
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)) {
            throw new GuliException(20001, "手机号或密码不能为空");
        }

        // 1. 验证手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) {
            throw new GuliException(20001, "改手机号未注册");
        }
        // 2. 验证密码是否正确
        // 数据库中的密码是进行加密之后的.因此需要 MD5加密之后在进行比较
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001, "密码错误");
        }

        // 3. 验证是否被禁用
        if (ucenterMember.getIsDisabled() == 1) {
            throw new GuliException(20001, "该用户被禁用");
        }

        // 4. 根据id和昵称生成 token
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
    }

    /**
     * @description 注册
     * @date 2022/9/4 12:38
     * @param registerVo
     * @return void
     */
    @Override
    public R register(RegisterVo registerVo) {
        // 获取数据
        String phone = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();

        // 判断数据是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001, "注册失败");
        }
        // 判断验证码是否失效
        String redis_code = (String) redisTemplate.opsForValue().get(phone);
        if (!code.equals(redis_code)) {
            throw new GuliException(20001, "验证码失效");
        }
        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone);
        if (baseMapper.selectCount(wrapper) > 0) {
            throw new GuliException(20001, "手机号重复");
        }

        // 保存数据库
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo, ucenterMember);
        ucenterMember.setAvatar("https://tse4-mm.cn.bing.net/th/id/OIP-C.FWcXMS8gv70TsJkGIHMjjgHaHa?pid=ImgDet&rs=1");
        // 密码需要进行加密
        ucenterMember.setPassword(MD5.encrypt(registerVo.getPassword()));

        return baseMapper.insert(ucenterMember) == 0 ? R.error().message("注册失败") : R.ok();


    }

    /**
     * @description 获取扫描二维码的用户信息，并保存到 token 中
     * @date 2022/9/5 18:17
     * @param code
     * @param state
     * @return java.lang.String
     */
    @Override
    public String callback(String code, String state) {
        try {
            // 第一次发送请求的地址
            String getTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 传递参数
            String finalTokenUrl = String.format(getTokenUrl,
                    WXConstantPropertiesUtil.WX_OPEN_APP_ID,
                    WXConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);

            // 使用 HttpClient 第一次发送请求: 根据 code 和 state 获取 token 和 openId
            // 最终得到一个获得 JSON格式的字符串，包含着 access_token openId
            String accessToken = HttpClientUtils.get(finalTokenUrl);
            Gson gson = new Gson();
            // 将 JSON 串转换成 map 集合
            HashMap tokenMap = gson.fromJson(accessToken, HashMap.class);
            // 获取 access_token,openId
            String access_token = (String) tokenMap.get("access_token");
            String openid = (String) tokenMap.get("openid");


            // 根据微信id，查询用户，如果能查出来说明已经注册过，查不出来进行注册
            QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid", openid);
            UcenterMember member = this.getOne(queryWrapper);
            if (member == null) {
                // 说明没有注册过，没有扫过码
                // 第二次发送请求
                String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String finalUserInfoUrl = String.format(getUserInfoUrl,
                        access_token, openid);

                // 根据 access_token, openid 访问微信的资源服务器，获取用户信息
                String userInfo = HttpClientUtils.get(finalUserInfoUrl);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                // 获取用户信息
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                // 注册
                this.save(member);
            } else {
                // 如果 member不等于null 说明注册过，判断用户是否被禁用
                if (member.getIsDisabled() == 1) {
                    throw new GuliException(20001, "用户被禁用");
                }
            }

            // 使用 jwt 生成 token 返回
            return JwtUtils.getJwtToken(member.getId(), member.getNickname());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "登陆失败");
        }
    }

    /**
     * @description 获取微信登录二维码
     * @date 2022/9/5 18:31
     * @param
     * @return java.lang.String
     */
    @Override
    public String getQRCode() {
        // 微信开放平台授权baseUrl
        // %s 相当于占位符，一个 %s 传一个参数
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对重定向的地址进行编码
        String redirect_url = WXConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 对 %s 进行传参: 第一个参数是对哪个字符串中的 %s 传参。 其余参数都是传的参数。
        String finalUrl = String.format(baseUrl, WXConstantPropertiesUtil.WX_OPEN_APP_ID, redirect_url, "guli");

        // 返回最终的跳转链接
        return finalUrl;
    }

    /**
     * @description 统计某天的注册人数
     * @date 2022/9/13 21:48
     * @param date
     * @return java.lang.Integer
     */
    @Override
    public Integer countRegister(String date) {
        return baseMapper.countRegister(date);

    }

}




