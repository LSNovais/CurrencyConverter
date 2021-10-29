
    INSERT INTO CURRENCY 
    (id_usuario, moeda_origem, valor_origem, moeda_destino, tx_conv_util, data_hora)
    VALUES 
    (1, 'BRL', 100.0, 'USD', 0.18002664, sysdate);

    INSERT INTO TransactionHist 
    (id_transaction_hist, id_usuario, valor_destino)
    VALUES 
    (1, 1, 200);
