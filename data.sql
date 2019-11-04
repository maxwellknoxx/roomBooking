-- ROOMS
INSERT INTO public.rooms (id, room_type) VALUES(1, 'SINGLE');
INSERT INTO public.rooms (id, room_type) VALUES(2, 'SINGLE');
INSERT INTO public.rooms (id, room_type) VALUES(3, 'DOUBLE');
INSERT INTO public.rooms (id, room_type) VALUES(4, 'DOUBLE');
INSERT INTO public.rooms (id, room_type) VALUES(5, 'SUITE');
INSERT INTO public.rooms (id, room_type) VALUES(6, 'SUITE');

-- BOOKINGS
INSERT INTO public.bookings (id, room_id) VALUES(1, 1);
INSERT INTO public.bookings (id, room_id) VALUES(2, 1);
INSERT INTO public.bookings (id, room_id) VALUES(3, 2);

-- BOOKINGS CALENDAR
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(1, '25/11/2019', '27/11/2019', '2019-11-25', 1);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(2, '25/11/2019', '27/11/2019', '2019-11-26', 1);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(3, '25/11/2019', '27/11/2019', '2019-11-27', 1);

INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(4, '15/11/2019', '20/11/2019', '2019-11-15', 2);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(5, '15/11/2019', '20/11/2019', '2019-11-16', 2);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(6, '15/11/2019', '20/11/2019', '2019-11-17', 2);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(7, '15/11/2019', '20/11/2019', '2019-11-18', 2);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(8, '15/11/2019', '20/11/2019', '2019-11-19', 2);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(9, '15/11/2019', '20/11/2019', '2019-11-20', 2);

INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(10, '05/11/2019', '06/11/2019', '2019-11-06', 3);
INSERT INTO public.booking_calendar (id, check_in, check_out, "day", booking_id) VALUES(11, '05/11/2019', '06/11/2019', '2019-11-07', 3);