package com.javacourse.task61;

import java.util.List;

public interface GuestBookController {
	void addRecord(String message);
	List<GuestBookEntity> getRecords();
}