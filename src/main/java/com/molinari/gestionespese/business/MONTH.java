package com.molinari.gestionespese.business;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MONTH {
	GENNAIO(1, 31),
	FEBBRAIO(2, 28),
	FEBBRAIO_BISESTILE(2, 29),
	MARZO(3,31),
	APRILE(4,30),
	MAGGIO(5,31),
	GIUGNO(6,30),
	LUGLIO(7,31),
	AGOSTO(8,31),
	SETTEMBRE(9,30),
	OTTOBRE(10,31),
	NOVEMBRE(11,30),
	DICEMBRE(12,31);


	private int days;
	private int monthIndex;

	private MONTH(int month, int days){
		this.monthIndex = month;
		this.days = days;
	}

	public int days(){
		return days;
	}

	public int month(){
		return monthIndex;
	}

	public static MONTH getMonth(int month, int anno){
		if(month < 1 || month > 12){
			throw new IllegalArgumentException("I mesi possono essere compresi da 1 a 12");
		}
		final Stream<MONTH> stream = Arrays.asList(values()).stream();
		if(month != 2){
			final Predicate<? super MONTH> predicate = m -> m.month() == month;
			return stream.filter(predicate).collect(Collectors.toList()).get(month);
		}else{
			final boolean leapYear = new GregorianCalendar().isLeapYear(anno);
			if(leapYear){
				return FEBBRAIO_BISESTILE;
			}
			return MONTH.FEBBRAIO;
		}
	}
}
