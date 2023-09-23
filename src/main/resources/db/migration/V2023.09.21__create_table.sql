create table if not exists news (
  id             bigserial,
  header         varchar(100),
  content        text,
  create_time_at time without time zone not null,
  create_date_at date not null,
  photo          bytea,
  primary key (id)
);

create unique index index_for_table_news on news(id, header);