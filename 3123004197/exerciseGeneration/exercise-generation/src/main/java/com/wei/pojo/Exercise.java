package com.wei.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
	private ArrayList<Data> datas;//题目中的运算数
	private ArrayList<Character> signs;//题目中的运算符
	private Data answer;//题目的答案
}
