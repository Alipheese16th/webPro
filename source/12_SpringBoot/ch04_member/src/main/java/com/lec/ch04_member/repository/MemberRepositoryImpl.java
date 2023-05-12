package com.lec.ch04_member.repository;

import com.lec.ch04_member.model.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private static Map<Integer, Member> store = new HashMap<>();
    private static int sequence = 0;

    @Override
    public int save(Member member) {
        member.setNo(++sequence);
        store.put(member.getNo(), member);
        return member.getNo();
    }

    @Override
    public Optional<Member> findByNo(int no) {
        return Optional.ofNullable(store.get(no));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){ // 메소드 하나 테스트할 때 실행
        store.clear();
    }


}
