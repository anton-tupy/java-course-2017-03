package com.javacourse.task61;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "GUEST_BOOK_TABLE")
@Data @AllArgsConstructor @NoArgsConstructor
public class GuestBookEntity {
	private Long id;
	private Date date;
	private String msg;

@Id
@GeneratedValue(generator="increment")
@GenericGenerator(name="increment", strategy = "increment")
public Long getId() {
	return id;
}

//@Temporal(TemporalType.TIMESTAMP)
//@Column(name = "DATE")
//public Date getDate() {
//	return date;
//}
}