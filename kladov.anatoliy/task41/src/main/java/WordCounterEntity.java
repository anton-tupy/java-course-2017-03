/**
 * Created by Anatoliy on 10.04.2017.
 */
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import java.util.Date;


@Entity
@Table(name = "WORD_COUNTER_TABLE")
@Data @AllArgsConstructor
public class WordCounterEntity {
	private Long id;
	private String word;
	private Integer occurrence;
	private Double freq;
	private Date date;

@Id
@GeneratedValue(generator="increment")
@GenericGenerator(name="increment", strategy = "increment")
public Long getId() {
	return id;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "DATE")
public Date getDate() {
	return date;
}
}