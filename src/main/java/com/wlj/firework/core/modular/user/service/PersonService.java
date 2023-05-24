package com.wlj.firework.core.modular.user.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.wlj.firework.core.context.ContextUtils;
import com.wlj.firework.core.modular.user.dao.MemberMapper;
import com.wlj.firework.core.modular.user.dao.UserMapper;
import com.wlj.firework.core.modular.user.manager.MemberManager;
import com.wlj.firework.core.modular.user.manager.UserManager;
import com.wlj.firework.core.modular.user.model.entity.Member;
import com.wlj.firework.core.modular.user.model.entity.User;
import com.wlj.firework.core.modular.user.request.PersonInfoEditRequest;
import com.wlj.firework.core.modular.common.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class PersonService {

    private final UserMapper userMapper;
    private final MemberMapper memberMapper;

    private final UserManager userManager;
    private final MemberManager memberManager;

    @Transactional(rollbackFor = Exception.class)
    public void editPersonInfo(PersonInfoEditRequest request) {
        String userId = ContextUtils.getCurrentUserId();
        User user = userManager.getByIdWithException(request.getUserId());
        JavaBeanUtils.map(request, user);
        if (StrUtil.isNotBlank(request.getNickName())) {
            user.setName(request.getNickName());
        }
        if (ObjectUtil.isNotNull(request.getGender())) {
            user.setGender(request.getGender().getCode());
        }
        user.setUpdateId(userId)
            .setUpdateTime(DateUtil.date());
        userMapper.updateById(user);

        Member member = memberManager.getByIdWithException(request.getUserId());
        JavaBeanUtils.map(request, member);
        if (ObjectUtil.isNotNull(request.getGender())) {
            member.setGender(request.getGender().getCode());
        }
        member.setUpdateId(userId)
              .setUpdateTime(DateUtil.date());
        memberMapper.updateById(member);
    }

}
