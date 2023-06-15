select * from sample_board  ;

select * from pg_catalog.pg_tables 
	where schemaname = 'kisteuportal'
	and tablename like 'tb%';
	
select * from tb_co_api order by api_no;

select ROW_NUMBER() OVER(order by api_no) AS ROWNUM, * from tb_co_api;

select * from (select ROW_NUMBER() OVER(order by api_no) AS ROWNUM, * from tb_co_api) as subtca
	where rownum between 11 and 20;

select * from tb_co_api order by api_no limit 10 offset 0;

select * from tb_co_api tca 
	inner join tb_co_api_exe_log tcael 
	on tca.api_no = tcael.api_no
	where tzon_nm = 'Asia/Seoul'
	order by exe_dt 
	limit 20 offset 10;

----------------------------------------------------

select aaa.table_schema as "스키마", aaa.table_name as "테이블(물리)", bbb.column_name as "컬럼(물리)", data_type as "데이터타입", character_maximum_length as "데이터 길이", is_nullable as "null여부", column_default as "기본값"
	from information_schema.tables as aaa
	right join 
	(SELECT * FROM INFORMATION_SCHEMA.columns where table_schema = 'kisteuportal') as bbb
	on aaa.table_name = bbb.table_name
	where aaa.table_schema = 'kisteuportal'
	order by aaa.table_name;

select *
	from information_schema.tables as aaa
	right join 
	(SELECT * FROM INFORMATION_SCHEMA.columns where table_schema = 'kisteuportal') as bbb
	on aaa.table_name = bbb.table_name
	where aaa.table_schema = 'kisteuportal'
	order by aaa.table_name;


select * from information_schema.columns where table_name = 'tb_co_api';

SELECT schemaname, tablename 
FROM pg_catalog.pg_tables 
WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema';


----- 완성해가는중
select 
	table_schema as "스키마", 
	table_name as "테이블(물리)", 
	(SELECT obj_description(table_name::regclass) AS "테이블(논리)"), 
	column_name as "컬럼(물리)", 
	(SELECT col_description(table_name::regclass, (SELECT attnum FROM pg_attribute WHERE attrelid = table_name::regclass AND attname = column_name)) AS "컬럼(논리)"), 
	data_type as "데이터타입", 
	character_maximum_length as "데이터 길이", 
	is_nullable as "null여부",
	column_default as "기본값"
	from INFORMATION_SCHEMA.columns as "asdf"
	where table_schema = 'kisteuportal' 
	order by table_name;

-----




 
select * from pg_catalog.pg_attribute;

-- 테이블주석
SELECT obj_description('sample_user'::regclass) AS table_comment;

select * from pg_class;

select * from sample_user;

-- 컬럼 attnum
SELECT attnum FROM pg_attribute WHERE attrelid = 'sample_user'::regclass AND attname = 'role';

-- 컬럼주석
SELECT col_description('sample_user'::regclass, (SELECT attnum FROM pg_attribute WHERE attrelid = 'sample_user'::regclass AND attname = 'role')) AS column_comment;

select * from pg_attribute;



-- pk 여부확인

SELECT
  a.attname AS column_name,
  CASE WHEN c.contype = 'p' THEN 'PK' ELSE '' END AS is_primary_key
FROM
  pg_attribute a
  LEFT JOIN pg_constraint c ON a.attrelid = c.conrelid AND a.attnum = ANY(c.conkey)
  LEFT JOIN pg_class t ON a.attrelid = t.oid
WHERE
  t.relname = 'tb_co_bbs'
  AND a.attname = 'bbs_doc_no'
  AND a.attnum > 0
  AND NOT a.attisdropped;

select * from pg_attribute;
select * from pg_constraint;
select * from pg_class;

SELECT
	  a.attname AS column_name,
	  CASE WHEN c.contype = 'p' THEN 'PK' ELSE '' END AS is_primary_key
	FROM
	  pg_attribute a
	  LEFT JOIN pg_constraint c ON a.attrelid = c.conrelid AND a.attnum = ANY(c.conkey)
	  LEFT JOIN pg_class t ON a.attrelid = t.oid
	WHERE
	  a.attnum > 0
	  AND NOT a.attisdropped
	ORDER BY
	  a.attnum;



---------------------------------


select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE;
select * from INFORMATION_SCHEMA.TABLE_CONSTRAINTS where constraint_type = 'PRIMARY KEY';
select * from pg_class;





------------------------------- 완성 ㅠㅠㅠ

SELECT 
    IC.table_schema as "스키마",
    IC.table_name as "테이블(물리명)",
    obj_description(IC.table_name::regclass) as "테이블(논리명)",
    IC.column_name as "컬럼(물리명)",
    col_description(IC.table_name::regclass, IC.ordinal_position) as "컬럼(논리명)",
    IC.ordinal_position as "컬럼순서",
    IC.data_type as "데이터 타입",
    IC.character_maximum_length as "데이터 길이",
    IC.is_nullable as "Null_허용여부",
    IC.column_default as "기본값",
    CASE WHEN PK.constraint_type = 'PRIMARY KEY' THEN 'Y' ELSE '' END AS "PK"
FROM 
    INFORMATION_SCHEMA.COLUMNS IC
    LEFT JOIN (
		SELECT 
	        kcu.column_name,
	        tc.table_name,
	        tc.constraint_type
	    FROM 
	        INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
	    join
	    	INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu 
	    on
	    	tc.constraint_name = kcu.constraint_name
	    WHERE
      		tc.constraint_type = 'PRIMARY KEY'
    	) PK
    ON
    	IC.column_name = PK.column_name
    AND 
    	IC.table_name = PK.table_name
WHERE 
    IC.table_schema = 'kisteuportal'
ORDER BY 
    IC.table_name, IC.ordinal_position;

--------------



   
   
   
   
   
   
   
   
   
   
   
   
   



















