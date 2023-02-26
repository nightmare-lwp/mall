package com.macro.mall.tiny.service.Impl;

import com.macro.mall.tiny.common.utils.JwtTokenUtil;
import com.macro.mall.tiny.dao.UmsAdminRoleRelationDao;
import com.macro.mall.tiny.mbg.mapper.UmsAdminMapper;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsAdminExample;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * UmsAdminService实现类
 *
 * @author lwp
 * @date 2023/2/22 21:05
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER=LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Resource
    UmsAdminMapper umsAdminMapper;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    UserDetailsService userDetailsService;
    @Resource
    JwtTokenUtil jwtTokenUtil;
    @Resource
    UmsAdminRoleRelationDao umsAdminRoleRelationDao;
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample umsAdminExample=new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins=umsAdminMapper.selectByExample(umsAdminExample);
        if(umsAdmins!=null&&umsAdmins.size()>0){
            return umsAdmins.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin=new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        UmsAdminExample umsAdminExample=new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdmins=umsAdminMapper.selectByExample(umsAdminExample);
        if(umsAdmins!=null&&umsAdmins.size()>0){
            return null;
        }
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token=null;
        try {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码错误");
            }
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token=jwtTokenUtil.generateToken(userDetails);
        }catch (AuthenticationException e){
            LOGGER.warn("登录异常：{}",e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return umsAdminRoleRelationDao.getPermissionList(adminId);
    }
}
