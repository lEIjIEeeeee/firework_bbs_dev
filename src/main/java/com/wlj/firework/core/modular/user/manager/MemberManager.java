package com.wlj.firework.core.modular.user.manager;

import cn.hutool.core.util.ObjectUtil;
import com.wlj.firework.core.modular.user.dao.MemberMapper;
import com.wlj.firework.core.modular.user.model.entity.Member;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberManager {

    private final MemberMapper memberMapper;

    public Member getById(String id) {
        return memberMapper.selectById(id);
    }

    public Member getByIdWithException(String id) {
        Member member = getById(id);
        if (ObjectUtil.isNull(member)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        return member;
    }

}
