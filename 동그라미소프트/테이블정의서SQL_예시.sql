
    SELECT pt.schemaname AS "스키마"
         , pt.tablename AS "테이블(물리명)"
         , pdt.description AS "테이블(논리명)"
         , pac.attname AS "컬럼(물리명)"
         , pdc.description AS "컬럼(논리명)"
         , isc.ordinal_position AS "컬럼순서"
         , isc.data_type AS "데이터타입"
         , isc.character_maximum_length AS "데이터길이"
         , CASE WHEN isc.is_nullable = 'NO' THEN 'N'
                ELSE 'Y'
           END AS "Null허용여부"
         , CASE WHEN pk.constraint_type <> '' THEN 'Y'
                ELSE ''
           END AS "PK"
         , isc.column_default AS "기본값"
      FROM pg_tables pt
      LEFT OUTER JOIN pg_stat_user_tables put
        ON put.schemaname = pt.schemaname
       AND put.relname = pt.tablename
      LEFT OUTER JOIN pg_description pdt
        ON put.relid = pdt.objoid
       AND pdt.objsubid = '0'
      LEFT OUTER JOIN pg_description pdc
        ON put.relid = pdc.objoid
       AND pdc.objsubid <> '0'
      LEFT OUTER JOIN pg_attribute pac
        ON pdc.objoid = pac.attrelid
       AND pdc.objsubid = pac.attnum
      LEFT OUTER JOIN information_schema."columns" isc
        ON isc.table_catalog = pt.schemaname
       AND isc.table_name = pt.tablename
       AND isc.column_name = pac.attname
      LEFT OUTER JOIN (
                       SELECT tc.table_schema
                            , tc.table_name
                            , tc.constraint_type
                            , ccu.column_name
                         FROM information_schema.table_constraints tc
                         JOIN information_schema.constraint_column_usage ccu
                           ON tc.table_catalog = ccu.table_catalog
                          AND tc.table_schema = ccu.table_schema
                          AND tc.table_name = ccu.table_name
                        WHERE tc.constraint_type = 'PRIMARY KEY'
                          AND tc.table_schema = 'kisteuportal'
           ) pk
        ON isc.table_schema = pk.table_schema
       AND isc.table_name = pk.table_name
       AND isc.column_name = pk.column_name
     WHERE pt.schemaname = 'kisteuportal'
     ORDER BY pt.tablename, isc.ordinal_position;
