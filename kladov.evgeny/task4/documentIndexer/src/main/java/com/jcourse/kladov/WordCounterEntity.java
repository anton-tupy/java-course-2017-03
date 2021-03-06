package com.jcourse.kladov;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORD_COUNTER_TABLE")
@Data @AllArgsConstructor
public class WordCounterEntity {
	private Long id;
	private String word;
	private Integer freq;
	private Double perc;

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}

}
