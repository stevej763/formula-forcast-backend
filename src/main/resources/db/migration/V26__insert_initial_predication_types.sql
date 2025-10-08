INSERT INTO public.prediction_type (prediction_type_uid, prediction_type, description, prediction_selection_type, created_at)
VALUES (
    'a31c131f-6fb6-4328-913e-33d40142101a',
    'FASTEST_LAP',
    'Predict which driver will set the fastest lap in the race',
    'DRIVER',
    NOW()
);

INSERT INTO public.prediction_type (prediction_type_uid, prediction_type, description, prediction_selection_type, created_at)
VALUES (
    'bce5ec8a-37b8-409c-82cd-57a5965fec2a',
    'DRIVER_OF_THE_DAY',
    'Predict which driver will be awarded the Driver of the Day',
    'DRIVER',
    NOW()
);

INSERT INTO public.prediction_type (prediction_type_uid, prediction_type, description, prediction_selection_type, created_at)
VALUES (
    '805a76ce-99a7-42f2-afab-eb75525ac683',
    'BIGGEST_WINNER',
    'Predict which driver make the most overtakes during the race',
    'DRIVER',
    NOW()
);

INSERT INTO public.prediction_type (prediction_type_uid, prediction_type, description, prediction_selection_type, created_at)
VALUES (
    '787be735-a278-480d-ba92-1c087ab84021',
    'BIGGEST_LOSER',
    'Predict which driver will be overtaken the most during the race',
    'DRIVER',
    NOW()
);

INSERT INTO public.prediction_type (prediction_type_uid, prediction_type, description, prediction_selection_type, created_at)
VALUES (
    '242a4d66-a0f7-4ea8-be10-3b29168d807a',
    'QUALIFYING_TOP_THREE',
    'Predict the top three drivers in qualifying',
    'DRIVER',
    NOW()
);

INSERT INTO public.prediction_type (prediction_type_uid, prediction_type, description, prediction_selection_type, created_at)
VALUES (
    'f629b01d-6ac7-4ac1-99e0-81c024ebe647',
    'RACE_TOP_THREE',
    'Predict the podium for the race',
    'DRIVER',
    NOW()
);