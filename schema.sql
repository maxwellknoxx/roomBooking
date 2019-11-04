CREATE TABLE public.rooms (
	id bigserial NOT NULL,
	room_type varchar(255) NULL,
	CONSTRAINT rooms_pkey PRIMARY KEY (id)
);

CREATE TABLE public.bookings (
	id bigserial NOT NULL,
	room_id int8 NULL,
	CONSTRAINT bookings_pkey PRIMARY KEY (id)
);
ALTER TABLE public.bookings ADD CONSTRAINT fkrgoycol97o21kpjodw1qox4nc FOREIGN KEY (room_id) REFERENCES rooms(id);

CREATE TABLE public.booking_calendar (
	id bigserial NOT NULL,
	check_in varchar(255) NULL,
	check_out varchar(255) NULL,
	"day" varchar(255) NULL,
	booking_id int8 NULL,
	CONSTRAINT booking_calendar_pkey PRIMARY KEY (id)
);
ALTER TABLE public.booking_calendar ADD CONSTRAINT fk5okx502t120hduy4dc75j7nr1 FOREIGN KEY (booking_id) REFERENCES bookings(id);