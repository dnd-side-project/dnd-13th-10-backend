package com.seed.domain.company.repository;

import com.seed.domain.company.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("""
        select c
        from Company c
        where lower(c.name) like lower(concat(:keyword, '%'))
           or lower(c.name) like lower(concat('%', :keyword, '%'))
        order by
          case when lower(c.name) like lower(concat(:keyword, '%')) then 0 else 1 end,
          c.name asc
        """)
    List<Company> searchCompanyByName(@Param("keyword") String keyword, Pageable pageable);

}
