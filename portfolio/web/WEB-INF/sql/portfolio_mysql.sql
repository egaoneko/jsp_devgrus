create database devgrus character set utf8 collate utf8_general_ci;

grant select, insert, update, delete, create, drop on devgrus.* to 'jspexam'@'localhost' identified by '1234';
grant select, insert, update, delete, create, drop on devgrus .* to 'jspexam'@'%' identified by '1234';

create table portfolio_id_sequence (
	sequence_name varchar(10) not null,
	next_value int not null,
	primary key (sequence_name)
);

insert into portfolio_id_sequence values ('article', 0);
insert into portfolio_id_sequence values ('comment', 0);

create table portfolio_article (
	article_id int not null auto_increment,
	group_id int not null,
	sequence_no char(16) not null,
	posting_date datetime not null,
	read_count int not null,
	writer_name varchar(20) not null,
	password varchar(10) not null,
	title varchar(100) not null,
	content text not null,
	comment_count int not null,
	primary key (article_id),
	index (sequence_no)
);


create table portfolio_comment(
	comment_id int not null auto_increment,
	group_id int not null,
	sequence_no char(16) not null,
	posting_date datetime not null,
	writer_name varchar(20) not null,
	password varchar(10) not null,
	content text not null,
	article_id int not null,
	primary key (comment_id),
	index (sequence_no),
	foreign key(article_id) references portfolio_article(article_id) on delete cascade
);

create table portfolio_guestbook (
    guestbook_id int not null auto_increment,
    guest_name varchar(50) not null,
    password varchar(10) not null,
    message text not null,
    primary key (message_id)
);