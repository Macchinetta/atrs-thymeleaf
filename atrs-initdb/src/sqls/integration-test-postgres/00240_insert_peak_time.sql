BEGIN;

CREATE OR REPLACE FUNCTION C_PEAK_TIME() RETURNS VOID AS $$
DECLARE

  currentTimestamp TIMESTAMP := current_timestamp;
  currentYear VARCHAR := date_part('YEAR', currentTimestamp);

  -- 登録する年数
  addingYear INT := 2;

  i INT;

BEGIN
  i := 0;
  WHILE i < addingYear LOOP

    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK1', to_date(currentYear || '/01/01', 'YYYY/MM/DD'), to_date(currentYear || '/01/05', 'YYYY/MM/DD'), 140);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK1', to_date(currentYear || '/03/20', 'YYYY/MM/DD'), to_date(currentYear || '/03/31', 'YYYY/MM/DD'), 140);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK1', to_date(currentYear || '/08/08', 'YYYY/MM/DD'), to_date(currentYear || '/08/18', 'YYYY/MM/DD'), 140);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK1', to_date(currentYear || '/12/26', 'YYYY/MM/DD'), to_date(currentYear || '/12/31', 'YYYY/MM/DD'), 140);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK2', to_date(currentYear || '/07/18', 'YYYY/MM/DD'), to_date(currentYear || '/08/07', 'YYYY/MM/DD'), 125);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK2', to_date(currentYear || '/08/19', 'YYYY/MM/DD'), to_date(currentYear || '/08/31', 'YYYY/MM/DD'), 125);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK2', to_date(currentYear || '/12/19', 'YYYY/MM/DD'), to_date(currentYear || '/12/25', 'YYYY/MM/DD'), 125);
    INSERT INTO PEAK_TIME (PEAK_TIME_CD, PEAK_START_DATE, PEAK_END_DATE, MULTIPLICATION_RATIO) VALUES('PEAK2', to_date(currentYear || '/03/13', 'YYYY/MM/DD'), to_date(currentYear || '/03/19', 'YYYY/MM/DD'), 125);

    currentTimestamp := currentTimestamp + '1 years';
    currentYear := date_part('YEAR', currentTimestamp);
    i := i + 1;
  END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT C_PEAK_TIME();

DROP FUNCTION C_PEAK_TIME();

COMMIT;
