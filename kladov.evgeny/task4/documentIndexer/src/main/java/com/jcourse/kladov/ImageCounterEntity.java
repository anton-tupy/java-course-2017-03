package com.jcourse.kladov;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE_COUNTER")
@Data @AllArgsConstructor
public class ImageCounterEntity {
	private Long id;
	private Integer total_Images;
	private Integer total_Documents;
	private Integer images_Per_Document;

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}
}
