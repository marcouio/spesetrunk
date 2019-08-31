package com.molinari.gestionespese.domain.wrapper;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;


public class WrapEntrateTest {

	@Test
	public void testSort() {
		WrapEntrate we = new WrapEntrate();
		IEntrate e1 = new Entrate();
		e1.setdata("2019/10/01");
		e1.setidEntrate(1);
		IEntrate e2 = new Entrate();
		e2.setdata("2019/10/02");
		e2.setidEntrate(2);
		IEntrate e3 = new Entrate();
		e3.setdata("2019/10/02");
		e3.setidEntrate(3);
		
		List<IEntrate> asList = Arrays.asList(e1,e2,e3);
		asList.sort((entr1, entr2) -> we.sortEntrata(entr1, entr2));
		
		IEntrate first = asList.get(0);
		Assert.assertEquals(3, first.getidEntrate());
		
		IEntrate second = asList.get(1);
		Assert.assertEquals(2, second.getidEntrate());

		IEntrate third = asList.get(2);
		Assert.assertEquals(1, third.getidEntrate());
		
	}

}
