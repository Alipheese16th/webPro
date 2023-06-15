


---- 
delete from tb_co_test_user_m;
delete from tb_co_test_user_dtl_l;
DROP sequence IF EXISTS tctudl_seq;


select * from tb_co_test_user_m tctum ;
select * from tb_co_test_user_dtl_l tctudl ;

select * 
	from tb_co_test_user_m tctum 
	join tb_co_test_user_dtl_l tctudl 
	on tctum.usr_key = tctudl.usr_key;


CREATE SEQUENCE tctudl_usr_key_seq START 1;




------ 회원키 가져오기
select 'USR' || to_char(current_date, 'YYYYMMDD') || '_' || LPAD(nextval('tctudl_usr_key_seq')::text,8,'0') as key;



------ 회원 첫등록시
insert into tb_co_test_user_m (usr_key, usr_id, usr_rgst_dt, crt_dtm, upt_dtm) 
	values ( 'USR20230614_00000020', 
			LOWER('AC001627'), 
			to_char(current_date, 'YYYYMMDD'), 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'), 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'));

insert into tb_co_test_user_dtl_l (usr_key, his_crt_dtm, his_seq, usr_nm, usr_eng_nm, crt_dtm)
	values ( 'USR20230614_00000020', 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'), 
			1, 
			'나나나', 
			'Na Mun Hee', 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'));

		
------ 회원 수정시
update tb_co_test_user_m 
	set usg_yn = 'Y',
		upt_dtm = to_char(current_timestamp, 'YYYYMMDDHH24MISS')
	where usr_key = 'USR20230613_00000001';


insert into tb_co_test_user_dtl_l (usr_key, his_crt_dtm, his_seq, usr_nm, usr_eng_nm, crt_dtm)
	values ('USR20230613_00000001', 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'), 
			(select max(his_seq)::integer + 1 from tb_co_test_user_dtl_l where usr_key = 'USR20230613_00000001'), 
			'신치윤', 
			'Shin Chee Yoon', 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'));


		
		
		
------ 모든 회원 조회
select um.usr_key, 
		um.usr_id, 
		ul.usr_nm, 
		ul.usr_eng_nm, 
		SUBSTRING(um.usr_rgst_dt FROM 1 FOR 4) || '-' || SUBSTRING(um.usr_rgst_dt FROM 5 FOR 2) || '-' || SUBSTRING(um.usr_rgst_dt FROM 7 FOR 2) AS usr_rgst_dt, 
		um.usg_yn
	from tb_co_test_user_m um
	join (
		select tctudl.usr_key, tctudl.his_seq, tctudl.usr_nm, tctudl.usr_eng_nm
		from tb_co_test_user_dtl_l tctudl
		inner join (SELECT usr_key, MAX(his_seq) AS max_his_seq FROM tb_co_test_user_dtl_l GROUP BY usr_key) sub
		on tctudl.usr_key = sub.usr_key
		and tctudl.his_seq = sub.max_his_seq
	) ul
	on um.usr_key = ul.usr_key;


------ 회원 검색
select um.usr_key, 
		um.usr_id, 
		ul.usr_nm, 
		ul.usr_eng_nm,
		to_char(to_date(um.usr_rgst_dt,'YYYYMMDD'),'YYYY-MM-DD') as usr_rgst_dt,
		um.usg_yn,
		to_char(to_timestamp(um.crt_dtm,'YYYYMMDDHH24MISS'),'YYYY-MM-DD HH24:MI:SS') as crt_dtm,
		to_char(to_timestamp(um.upt_dtm,'YYYYMMDDHH24MISS'),'YYYY-MM-DD HH24:MI:SS') as upt_dtm
	from tb_co_test_user_m um
	join (
		select tctudl.usr_key, tctudl.his_seq, tctudl.usr_nm, tctudl.usr_eng_nm
		from tb_co_test_user_dtl_l tctudl
		inner join (SELECT usr_key, MAX(his_seq) AS max_his_seq FROM tb_co_test_user_dtl_l GROUP BY usr_key) sub
		on tctudl.usr_key = sub.usr_key
		and tctudl.his_seq = sub.max_his_seq
	) ul
	on um.usr_key = ul.usr_key
	where um.del_yn = 'N'
	and um.usr_key ilike 'USR20230613_00000001'
	and um.usr_id ILIKE 'shwoo93'
	and (ul.usr_nm ILIKE '%' ||'shin' || '%' or ul.usr_eng_nm ilike '%' ||'shin' || '%')
	and to_timestamp(um.usr_rgst_dt,  'YYYYMMDDHH24MIss') >= '2023-06-10'::timestamp 
	and to_timestamp(um.usr_rgst_dt,  'YYYYMMDDHH24MIss') <= concat('2023-06-20', ' 23:59:59')::timestamp
	and um.usg_yn = 'Y'
	order by um.upt_dtm desc;



select * from tb_co_test_user_dtl_l tctudl ;

select row_number() over(partition by usr_key order by his_seq desc), * from tb_co_test_user_dtl_l;



---- 회원 삭제
update tb_co_test_user_m 
	set del_yn = 'Y',
		upt_dtm = to_char(current_timestamp, 'YYYYMMDDHH24MISS')
	where usr_key = 'USR20230613_00000001';

insert into tb_co_test_user_dtl_l (usr_key, his_crt_dtm, his_seq, usr_nm, usr_eng_nm, del_yn, crt_dtm)
	values ('USR20230613_00000001', 
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'), 
			(select max(his_seq)::integer + 1 from tb_co_test_user_dtl_l where usr_key = 'USR20230613_00000001'),  
			'신치윤',
			'shin',
			'Y',
			to_char(current_timestamp, 'YYYYMMDDHH24MISS'));


select * from tb_co_test_user_m;
select * from tb_co_test_user_dtl_l tctudl ;





