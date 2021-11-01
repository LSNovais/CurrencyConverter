
    INSERT INTO CURRENCY 
    (id_currency, id_usuer, currency_origin, value_origin, currency_destiny, tx_conv_util, date_hour)
   VALUES 
    (1, 1, 'BRL', 100.0, 'USD', 0.18002664, sysdate);

    INSERT INTO TransactionHist 
    (id_transaction_hist, id_user, val_destiny)
    VALUES 
    (1, 1, 200);
