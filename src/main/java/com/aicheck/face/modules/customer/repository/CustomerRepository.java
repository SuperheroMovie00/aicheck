/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.repository;

import com.aicheck.face.modules.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:54 PM 2019/1/23
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Transactional
    List<Customer> deleteByIdIn(List<Integer> idList);

    List<Customer> findByIdIn(List<Integer> ids);

    @Query(value = "select max(member_id) from customer",nativeQuery = true)
    String findByMaxCode();
    
    Customer findByMobile(String mobile);


    @Query(value = "select IFNULL(a.amount,0) as amount,a.name,a.create_time as createTime from ( select sum(p.price * sd.quantity) as amount,c.name,c.create_time from customer_tags ct left join customer c on ct.customer_id = c.id " +
            "left join sales_record sr on c.id = sr.customer_id and sr.create_time >= ?1 left join sales_detail sd on sr.id = sd.sales_id " +
            "left join product p on sd.product_id = p.id where ct.tag_id = ?2 " +
            " group by ct.customer_id ) a",nativeQuery = true)
    List<Map<String,Object>> statisticalTeam(Date date, Integer tagId);

    @Query(value = "select c.total,n.newNumber,if(a.sales is null ,0,a.sales) as sales from (select count(id) as total from identify_record where create_time >= ?1) c , " +
            "(select count(id) as newNumber from customer where create_time >= ?1) n ," +
            "(select sum(a.sales) as sales from (select sum(p.price * sd.quantity) as sales from sales_detail sd left join product p on sd.product_id = p.id where sd.create_time >= ?1  group by sales_id ) a)a "
            ,nativeQuery = true)
    Object[] statisticalInit(Date date);

    @Query(value = "select * from (select count(id) as men from customer where gender = '男')a,(select count(id) as women from customer where  gender = '女') b",nativeQuery = true)
    Map<String,Object> statisticalGender();
    
    
    @SuppressWarnings("rawtypes")
	@Query(value = "SELECT a.*,c.`name` as 'na' from customer a INNER JOIN customer_tags b on a.id=b.customer_id INNER JOIN tags c on b.tag_id=c.id",nativeQuery = true)
    List<Map> ccccc();

    
    @Query(value = "select * from (select count(id) as men from visitors_record where gender = '男' and create_time >= curdate()) a,(select count(id) as women from visitors_record where  gender = '女' and create_time >= curdate()) b " +
            "union all " +
            "select * from (select count(ir.id) as men from identify_record ir left join customer c on ir.customer_id = c.id where c.gender = '男' and ir.create_time >= curdate())a,(select count(ir.id) as women from identify_record ir left join customer c on ir.customer_id = c.id where  c.gender = '女' and ir.create_time >= curdate()) b",nativeQuery = true)
    List<Map<String,Object>> statisticalGenderCount(Date date);
    
 

    @Query(value = "select * from (select count(id) as number1 from visitors_record where age > 0 and age <= 12 and create_time >= curdate()) a, " +
            "            (select count(id) as number2 from visitors_record where age >= 13 and age <= 18 and create_time >= curdate()) b, " +
            "            (select count(id) as number3 from visitors_record where age >= 19 and age <= 30 and create_time >= curdate()) c, " +
            "            (select count(id) as number4 from visitors_record where age >= 31 and age <= 50 and create_time >= curdate()) d, " +
            "            (select count(id) as number5 from visitors_record where age >= 51 and age <= 70 and create_time >= curdate()) e, " +
            "            (select count(id) as number6 from visitors_record where age > 70 and create_time >= curdate()) f " +
            "            union all " +
            "  select * from (select count(ir.id) as number1 from identify_record ir left join customer c on ir.customer_id = c.id where c.age > 0 and c.age <= 12 and ir.create_time >= curdate()) a, " +
            "            (select count(ir.id) as number2 from identify_record ir left join customer c on ir.customer_id = c.id   where c.age >= 13 and c.age <= 18 and ir.create_time >= curdate()) b, " +
            "            (select count(ir.id) as number3 from identify_record ir left join customer c on ir.customer_id = c.id  where c.age >= 19 and c.age <= 30 and ir.create_time >= curdate()) c, " +
            "            (select count(ir.id) as number4 from identify_record ir left join customer c on ir.customer_id = c.id  where c.age >= 31 and c.age <= 50 and ir.create_time >= curdate()) d, " +
            "            (select count(ir.id) as number5 from identify_record ir left join customer c on ir.customer_id = c.id  where c.age >= 51 and c.age <= 70 and ir.create_time >= curdate()) e, " +
            "            (select count(ir.id) as number6 from identify_record ir left join customer c on ir.customer_id = c.id  where c.age > 70 and ir.create_time >= curdate()) f",nativeQuery = true)
    List<Map<String,Object>> statisticalAgeCount(Date date);

    
    @Query(value = "select * from (select count(id) as oldCount from visitors_record where create_time >= ?1) a," +
            "(select count(id) as newCount from identify_record where create_time >= ?1)b",nativeQuery = true)
    Map<String,Object> statisticalPassengerFlowCount(Date date);


    /**
     * 重写如下
     * @return
     */
 /*   @Query(value = "select * from (select count(id) as number1 from customer where age > 0 and age <= 12) a," +
            "(select count(id) as number2 from customer where age >= 13 and age <= 18) b," +
            "(select count(id) as number3 from customer where age >= 19 and age <= 30) c," +
            "(select count(id) as number4 from customer where age >= 31 and age <= 50) d," +
            "(select count(id) as number5 from customer where age >= 51 and age <= 70) e," +
            "(select count(id) as number6 from customer where age > 70) f",nativeQuery = true)
    Object[] statisticalAge();
*/
    
    @Query(value = "select * from (select count(id) as number1 from visitors_record where age > 0 and age <= 12 and create_time >= curdate()) a," +
            "(select count(id) as number2 from visitors_record where age >= 13 and age <= 18 and create_time >= curdate()) b," +
            "(select count(id) as number3 from visitors_record where age >= 19 and age <= 30 and create_time >= curdate()) c," +
            "(select count(id) as number4 from visitors_record where age >= 31 and age <= 50 and create_time >= curdate()) d," +
            "(select count(id) as number5 from visitors_record where age >= 51 and age <= 70 and create_time >= curdate()) e," +
            "(select count(id) as number6 from visitors_record where age > 70) f",nativeQuery = true)
    Object[] statisticalAge();
    
    
    @Query(value = "SELECT dayHour as hour,IF ( count IS NULL, 0, count ) as count  FROM ( SELECT count( * ) AS count, DATE_FORMAT( create_time, '%H' ) AS HOUR FROM identify_record where create_time >= ?1 and create_time <= ?2 GROUP BY HOUR ORDER BY 1 ) A \tRIGHT JOIN ( SELECT\tone.hours + two.hours AS dayHour FROM\t( SELECT\t0 hours UNION ALL SELECT\t1 hours UNION ALL  SELECT\t2 hours UNION ALL SELECT 3 hours UNION ALL SELECT 4 hours UNION ALL SELECT\t5 hours UNION ALL SELECT 6 hours UNION ALL SELECT 7 hours UNION ALl SELECT 8 hours UNION ALL " +
            "SELECT 9 hours  ) one CROSS JOIN ( SELECT 0 hours UNION ALL SELECT 10 hours UNION ALL SELECT 20 hours ) two  WHERE ( one.hours + two.hours ) < 24 ) B ON A.HOUR = CONVERT ( B.dayHour, SIGNED ) ORDER BY dayHour",nativeQuery = true)
    List<Map<String,Object>> statisticalOldPassengerFlowByDay(Date startTime,Date endTime);

    
    
/*    @Query(value = "select DAY(ir.create_time) as day,count(ir.id) as count from identify_record ir " +
            "where ir.create_time >= ?1 and ir.create_time <= ?2 group by DAY(ir.create_time) order by DAY(ir.create_time)",nativeQuery = true)
    List<Map<String,Object>> statisticalOldPassengerFlowByWeek(Date startTime,Date endTime);
*/
    
    
    @Query(value = "select DAY(ir.create_time) as day,count(ir.id) as count from identify_record ir " +
            "where ir.create_time >= ?1 and ir.create_time <= ?2 group by DAY(ir.create_time) order by DAY(ir.create_time)",nativeQuery = true)
    List<Map<String,Object>> statisticalOldPassengerFlowByMonth(Date startTime,Date endTime);

    

    @Query(value = "SELECT dayHour as hour,IF ( count IS NULL, 0, count ) as count  FROM ( SELECT count( * ) AS count, DATE_FORMAT( create_time, '%H' ) AS HOUR FROM visitors_record where create_time >= ?1 and create_time <= ?2 GROUP BY HOUR ORDER BY 1 ) A \tRIGHT JOIN ( SELECT\tone.hours + two.hours AS dayHour FROM\t( SELECT\t0 hours UNION ALL SELECT\t1 hours UNION ALL  SELECT\t2 hours UNION ALL SELECT 3 hours UNION ALL SELECT 4 hours UNION ALL SELECT\t5 hours UNION ALL SELECT 6 hours UNION ALL SELECT 7 hours UNION ALl SELECT 8 hours UNION ALL " +
            "SELECT 9 hours  ) one CROSS JOIN ( SELECT 0 hours UNION ALL SELECT 10 hours UNION ALL SELECT 20 hours ) two  WHERE ( one.hours + two.hours ) < 24 ) B ON A.HOUR = CONVERT ( B.dayHour, SIGNED ) ORDER BY dayHour",nativeQuery = true)
    List<Map<String,Object>> statisticalNewPassengerFlowByDay(Date startTime,Date endTime);

    
    
    @Query(value = "select DAY(ir.create_time) as day,count(ir.id) as count from visitors_record ir " +
            "where   ir.create_time >= ?1 and ir.create_time <= ?2 group by DAY(ir.create_time) order by DAY(ir.create_time)",nativeQuery = true)
    List<Map<String,Object>> statisticalNewPassengerFlowByWeek(Date startTime,Date endTime);

    @Query(value = "select DAY(ir.create_time) as day,count(ir.id) as count from visitors_record ir " +
            "where   ir.customer_id<>0 and ir.customer_id is NOT null and ir.create_time >= ?1 and ir.create_time <= ?2 group by DAY(ir.create_time) order by DAY(ir.create_time)",nativeQuery = true)
    List<Map<String,Object>> statisticalOldPassengerFlowByWeek(Date startTime,Date endTime);
    
    
    @Query(value = "select DAY(ir.create_time) as day,count(ir.id) as count from visitors_record ir " +
            "where ir.create_time >= ?1 and ir.create_time <= ?2 group by DAY(ir.create_time) order by DAY(ir.create_time)",nativeQuery = true)
    List<Map<String,Object>> statisticalNewPassengerFlowByMonth(Date startTime,Date endTime);

    
    
}
