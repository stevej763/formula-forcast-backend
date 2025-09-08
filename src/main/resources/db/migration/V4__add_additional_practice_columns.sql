ALTER TABLE public.practice_session ADD COLUMN practice_session_number INTEGER NOT NULL;

ALTER TABLE public.practice_session ADD CHECK (practice_session_number BETWEEN 1 AND 3);