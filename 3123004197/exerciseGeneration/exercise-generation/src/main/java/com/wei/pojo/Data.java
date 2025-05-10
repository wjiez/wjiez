package com.wei.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Objects;


@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data{
	private Integer itg;//整数部分
	private Integer numerator;//分子部分
	private Integer denominator;//分母部分

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Data data = (Data) o;
		return Objects.equals(itg, data.itg)
				&& Objects.equals(numerator, data.numerator)
				&& Objects.equals(denominator, data.denominator);
	}

}

