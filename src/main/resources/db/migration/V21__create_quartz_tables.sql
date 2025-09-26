CREATE SCHEMA quartz;

SET search_path TO quartz;

CREATE TABLE quartz.qrtz_job_details
(
    sched_name character varying(120) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    job_class_name character varying(250) NOT NULL,
    is_durable boolean NOT NULL,
    is_nonconcurrent boolean NOT NULL,
    is_update_data boolean NOT NULL,
    requests_recovery boolean NOT NULL,
    job_data bytea,
    CONSTRAINT pk_qrtz_job_details_ PRIMARY KEY (sched_name, job_name, job_group)
);

CREATE TABLE quartz.qrtz_triggers
(
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority integer,
    trigger_state character varying(16) NOT NULL,
    trigger_type character varying(8) NOT NULL,
    start_time bigint NOT NULL,
    end_time bigint,
    calendar_name character varying(200),
    misfire_instr smallint,
    job_data bytea,
    CONSTRAINT qrtz_triggers_pk PRIMARY KEY (sched_name, trigger_name, trigger_group),
    CONSTRAINT qrtz_triggers_fk FOREIGN KEY (job_group, sched_name, job_name)
        REFERENCES quartz.qrtz_job_details (job_group, sched_name, job_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
);

CREATE TABLE quartz.qrtz_simple_triggers
(
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    repeat_count bigint NOT NULL,
    repeat_interval bigint NOT NULL,
    times_triggered bigint NOT NULL,
    CONSTRAINT qrtz_simple_triggers_pk PRIMARY KEY (sched_name, trigger_name, trigger_group),
    CONSTRAINT qrtz_simple_triggers_fk FOREIGN KEY (trigger_name, trigger_group, sched_name)
        REFERENCES qrtz_triggers (trigger_name, trigger_group, sched_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
);

CREATE TABLE quartz.qrtz_simprop_triggers
(
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    str_prop_1 character varying(512),
    str_prop_2 character varying(512),
    str_prop_3 character varying(512),
    int_prop_1 integer,
    int_prop_2 integer,
    long_prop_1 bigint,
    long_prop_2 bigint,
    dec_prop_1 numeric,
    dec_prop_2 numeric,
    bool_prop_1 boolean,
    bool_prop_2 boolean,
    time_zone_id character varying(80),
    CONSTRAINT qrtz_simprop_triggers_pk PRIMARY KEY (sched_name, trigger_name, trigger_group),
    CONSTRAINT qrtz_simprop_triggers_fk FOREIGN KEY (sched_name, trigger_name, trigger_group)
        REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
);

CREATE TABLE quartz.qrtz_cron_triggers
(
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    cron_expression character varying(120) NOT NULL,
    time_zone_id character varying(80),
    CONSTRAINT qrtz_cron_triggers_pk PRIMARY KEY (sched_name,  trigger_name, trigger_group),
    CONSTRAINT qrtz_cron_triggers_fk FOREIGN KEY (trigger_name, trigger_group, sched_name)
        REFERENCES qrtz_triggers (trigger_name, trigger_group, sched_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
);

CREATE TABLE quartz.qrtz_blob_triggers
(
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    blob_data bytea,
    CONSTRAINT qrtz_blob_triggers_pk PRIMARY KEY (sched_name, trigger_name, trigger_group),
    CONSTRAINT qrtz_blob_triggers_fk FOREIGN KEY (trigger_name, trigger_group, sched_name)
        REFERENCES qrtz_triggers (trigger_name, trigger_group, sched_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
);

CREATE TABLE quartz.qrtz_calendars
(
    sched_name character varying(120) NOT NULL,
    calendar_name character varying(200) NOT NULL,
    calendar bytea NOT NULL,
    CONSTRAINT qrtz_calendars_pk PRIMARY KEY (sched_name, calendar_name)
);

CREATE TABLE quartz.qrtz_paused_trigger_grps
(
    sched_name character varying(120) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    CONSTRAINT qrtz_paused_trigger_grps_pk PRIMARY KEY (sched_name, trigger_group)
);

CREATE TABLE quartz.qrtz_fired_triggers
(
    sched_name character varying(120) NOT NULL,
    entry_id character varying(140) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    instance_name character varying(200) NOT NULL,
    fired_time bigint NOT NULL,
    sched_time bigint NOT NULL,
    priority integer NOT NULL,
    state character varying(16) NOT NULL,
    job_name character varying(200),
    job_group character varying(200),
    is_nonconcurrent boolean,
    requests_recovery boolean,
    CONSTRAINT qrtz_fired_triggers_pk PRIMARY KEY (sched_name, entry_id)
);

CREATE TABLE quartz.qrtz_scheduler_state
(
    sched_name character varying(120) NOT NULL,
    instance_name character varying(200) NOT NULL,
    last_checkin_time bigint NOT NULL,
    checkin_interval bigint NOT NULL,
    CONSTRAINT qrtz_scheduler_state_pk PRIMARY KEY (sched_name, instance_name)
);

CREATE TABLE quartz.qrtz_locks
(
    sched_name character varying(120) NOT NULL,
    lock_name character varying(40) NOT NULL,
    CONSTRAINT qrtz_locks_pk PRIMARY KEY (sched_name, lock_name)
);