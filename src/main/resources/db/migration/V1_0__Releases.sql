create table releases (
  id varchar(5000) primary key,
  author varchar(5000),
  title varchar(5000),
  date timestamp,
  content varchar(10000000),

  repo_name varchar(5000),
  repo_description varchar(5000),
  repo_url varchar(5000)
);
