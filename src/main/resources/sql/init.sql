
drop table if exists stations;
drop table if exists weather;

create table if not exists stations
(
    id      VARCHAR(36) unique primary key DEFAULT gen_random_uuid(),
    name    varchar(256),
    station_code varchar(36),
    country varchar(256)
);

create table if not exists weather
(
    id              VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid(),
    temp_c          integer,
    delta_temp      integer,
    wind_kph        integer,
    wind_dir        varchar(2),
    cloud_okt       varchar(3),
    cloud_type      varchar(2),
    conditions_text varchar(256),
    condition_code  integer,
    station_code    varchar(36),
    created_at      timestamp,
    record_created_at timestamp default now()
);
