package com.exercise.tal.oligarch.rating;

import com.exercise.tal.oligarch.rating.model.Oligarch;
import com.exercise.tal.oligarch.rating.repository.OligarchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;

@DataJpaTest
class OligarchRepositoryTest {
	@Autowired
	private OligarchRepository repository;

	@Test
	void testFindRankByAssetsValue() {
		repository.save(new Oligarch(1L, "A","B", new BigDecimal("10")));
		repository.save(new Oligarch(2L, "C","D", new BigDecimal("20")));
		repository.save(new Oligarch(3L, "E","F", new BigDecimal("30")));
		assertThat(repository.findRankByAssetsValue(new BigDecimal("20"))).isEqualTo(2);
	}
}
