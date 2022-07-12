package jpabook.jpashop.service;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class) //Junit 실행할 때 스프링이랑 같이 실행한다는 뜻
@SpringBootTest //SpringBoot를 띄운 상태로 실행할 때. 없으면 @Autowired 못씀.
@Transactional // 테스트 끝나면 전부 Rollback 함.
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception {
	    //given
		Member member = new Member();
		member.setName("kim");

	    //when
		Long saveId = memberService.join(member);

	    //then
		assertEquals(member, memberRepository.findOne(saveId));
	}

	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() throws Exception {
	    //given
		Member member1 = new Member();
		member1.setName("kim");

		Member member2 = new Member();
		member2.setName("kim");

	    //when
		memberService.join(member1);
		memberService.join(member2); //예외가 발생해야 한다!!!

	    //then
		fail("예외가 발생해야 한다.");
	}
}
