(ns clj-iso8583.parser-test
  (:require [clj-iso8583.binary :as binary]
            [clj-iso8583.parser :as parser]
            [clj-iso8583.format :as format]
            [clj-iso8583.format-iso8583 :as format-iso8583])
  (:use clojure.test midje.sweet))

(def full-message
  (clojure.string/join 
    ["30323030f23e46d529e0910000000000" "10000022313635383133333930303036" "34333333323130313130303030303030" "30303030363636303130303931353032"
     "31393032383833383131303231393130" "30393135303931303039363031313035" "31303031303031324330303030303030" "30433030303030303030313131323430"
     "30313030303130333735383133333930" "30303634333333323144313530393232" "30363434303030303031303030313136" "30303230303030303030323230494e54"
     "3031323334494e543030303031303030" "31202020343638205448524553484f4c" "44204156454e55452020453531303620" "494e5420435459204f4e434131323474"
     "2b95779b5a223f303034313531303131" "30323030363030303330353031353531" "31323031353135303031303032303031" "32353260101480200000003130303030"
     "30303034373530474931303030333035" "53537243494e54303153536e6b202030" "32383833383032383833384949303830" "3930345354746c303352424332303132"
     "313030393030313830323138506f7374" "696c696f6e3a4d657461446174613233" "303138546674693a5549443131313231" "315445524d494e414c5f494431313131"
     "38546674693a5549443238303c546674" "695549443e3c5549443e544f5247534c" "47493130303033303553537230323030" "3a3032383833383a3130303931353032"
     "31393a3030363433333332313c2f5549" "443e3c2f546674695549443e32313154" "45524d494e414c5f4944323136494e54" "30313233342020202020202020303937"
     "37313749636344617461333936343c3f" "786d6c2076657273696f6e3d22312e30" "2220656e636f64696e673d225554462d" "38223f3e3c496363446174613e3c4963"
     "63526571756573743e3c416d6f756e74" "417574686f72697a65643e3030303030" "303030363135303c2f416d6f756e7441" "7574686f72697a65643e3c416d6f756e"
     "744f746865723e303030303030303030" "3030303c2f416d6f756e744f74686572" "3e3c4170706c69636174696f6e496e74" "65726368616e676550726f66696c653e"
     "313830303c2f4170706c69636174696f" "6e496e7465726368616e676550726f66" "696c653e3c4170706c69636174696f6e" "5472616e73616374696f6e436f756e74"
     "65723e303033363c2f4170706c696361" "74696f6e5472616e73616374696f6e43" "6f756e7465723e3c43727970746f6772" "616d3e33333946413532324644374646"
     "3135363c2f43727970746f6772616d3e" "3c5465726d696e616c436f756e747279" "436f64653e3132343c2f5465726d696e" "616c436f756e747279436f64653e3c54"
     "65726d696e616c566572696669636174" "696f6e526573756c743e383038303034" "383030303c2f5465726d696e616c5665" "72696669636174696f6e526573756c74"
     "3e3c5472616e73616374696f6e437572" "72656e6379436f64653e3132343c2f54" "72616e73616374696f6e43757272656e" "6379436f64653e3c5472616e73616374"
     "696f6e446174653e3130303431363c2f" "5472616e73616374696f6e446174653e" "3c5472616e73616374696f6e54797065" "3e30313c2f5472616e73616374696f6e"
     "547970653e3c556e7072656469637461" "626c654e756d6265723e374243433039" "37363c2f556e7072656469637461626c" "654e756d6265723e3c4170706c696361"
     "74696f6e4964656e7469666965723e41" "303030303030323737313031303c2f41" "70706c69636174696f6e4964656e7469" "666965723e3c43766d526573756c7473"
     "3e3032303330303c2f43766d52657375" "6c74733e3c4973737565724170706c69" "636174696f6e446174613e3031313041" "30303030333232303030303030303030"
     "3030303030303030303030303030463c" "2f4973737565724170706c6963617469" "6f6e446174613e3c5465726d696e616c" "4361706162696c69746965733e363034"
     "3032303c2f5465726d696e616c436170" "6162696c69746965733e3c4368697043" "6f6e646974696f6e436f64653e303c2f" "43686970436f6e646974696f6e436f64"
     "653e3c43727970746f6772616d496e66" "6f726d6174696f6e446174613e38303c" "2f43727970746f6772616d496e666f72" "6d6174696f6e446174613e3c2f496363"
     "526571756573743e3c2f496363446174" "613e3033494e54"]))

(defn parsed-message [] 
  (let [parser (parser/parser (format-iso8583/field-definitions))] 
    (parser (binary/hex-to-bytes full-message))))

(fact "Can extract the message-type"
  (:message-type (parsed-message)) => "0200")

(fact "Can extract the pan"
  (:pan (parsed-message)) => "5813390006433321")

(fact "Can extract the processing code"
  (:processing-code (parsed-message)) => "011000")

(fact "Can extract the transaction amount"
  (:transaction-amount (parsed-message)) => "000000006660")

(fact "Can extract the transmission date-time"
  (:transmission-date-time (parsed-message)) => "1009150219")

(fact "Can extract the stan"
  (:stan (parsed-message)) => "028838")

(fact "Can extract the local transaction time"
  (:local-transaction-time (parsed-message)) => "110219")

(fact "Can extract the local transaction date"
  (:local-transaction-date (parsed-message)) => "1009")

(fact "Can extract the card expiry date"
  (:card-expiry-date (parsed-message)) => "1509")

(fact "Can extract the transaction settlement date"
  (:transaction-settlement-date (parsed-message)) => "1009")

(fact "Can extract the merchant type"
  (:merchant-type (parsed-message)) => "6011")

(fact "Can extract the pos entry mode"
  (:pos-entry-mode (parsed-message)) => "051")

(fact "Can extract the card sequence number"
  (:card-sequence-number (parsed-message)) => "001")

(fact "Can extract the pos condition code"
  (:pos-condition-code (parsed-message)) => "00")

(fact "Can extract the pos capture code"
  (:pos-capture-code (parsed-message)) => "12")

(fact "Can extract the transaction fee amount"
  (:transaction-fee-amount (parsed-message)) => "C00000000")

(fact "Can extract the transaction processing fee"
  (:transaction-processing-fee (parsed-message)) => "C00000000")

(fact "Can extract the acquiring institution id code"
  (:acquiring-institution-id-code (parsed-message)) => "12400100010")

(fact "Can extract the track 2"
  (:track-2 (parsed-message)) => "5813390006433321D15092206440000010001")

(fact "Can extract the retrieval reference number"
  (:retrieval-reference-number (parsed-message)) => "160020000000")

(fact "Can extract the service restriction code"
  (:service-restriction-code (parsed-message)) => "220")

(fact "Can extract the terminal id"
  (:terminal-id (parsed-message)) => "INT01234")

(fact "Can extract the card acceptor id"
  (:card-acceptor-id (parsed-message)) => "INT000010001   ")

(fact "Can extract the card acceptor name location"
  (:card-acceptor-name-location (parsed-message)) => "468 THRESHOLD AVENUE  E5106 INT CTY ONCA")

(fact "Can extract the transaction currency"
  (:transaction-currency (parsed-message)) => "124")

(fact "Can extract the pin data"
  (:pin-data (parsed-message)) => "742B95779B5A223F")

(fact "Can extract the message reason code"
  (:message-reason-code (parsed-message)) => "1510")

(fact "Can extract the receiving institution id code"
  (:receiving-institution-id-code (parsed-message)) => "02006000305")

(fact "Can extract the pos data code"
  (:pos-data-code (parsed-message)) => "511201515001002")

(fact "Can extract something from the tertiary bitmap"
  (let [parser (parser/parser (format/make-field-definitions [[130 :high-field (format/fixed-length-field 3)]]))] 
    (:high-field (parser (binary/hex-to-bytes "30323030800000000000000080000000000000004000000000000000313233")))) => "123")

(fact "Too much data is reported as a validation error"
  (let [parser (parser/parser (format/make-field-definitions [[2 :field (format/fixed-length-field 3)]]))
        result (parser (binary/hex-to-bytes "3032303040000000000000003132333435"))] 
    (:field result) => "123"
    (:is-valid? result) => false
    (:errors result) => ["Trailing data found after message: '0x3435'"]))

(fact "A field too short is reported as a validation error"
  (let [parser (parser/parser (format/make-field-definitions [[2 :field (format/fixed-length-field 3)]]))
        result (parser (binary/hex-to-bytes "3032303040000000000000003132"))] 
    (:is-valid? result) => false
    (:errors result) => ["(field-name) Error: Less than 3 bytes available. The data: [3132]"]))

(fact "When the message type is too short it is reported as a validation error"
  (let [parser (parser/parser (format/make-field-definitions []))
        result (parser (binary/hex-to-bytes "303230"))] 
    (:is-valid? result) => false
    (:errors result) => ["(message-type) Error: Insufficient data. The data: [303230]"]))

;0200:
;   [LLVAR  n    ..19 016] 002 [5813390006433321]
;   [Fixed  n       6 006] 003 [011000]
;   [None   n         012] 004 [000000006660]
;   [Fixed  n      10 010] 007 [1009150219]
;   [Fixed  n       6 006] 011 [028838]
;   [Fixed  n       6 006] 012 [110219]
;   [Fixed  n       4 004] 013 [1009]
;   [Fixed  n       4 004] 014 [1509]
;   [Fixed  n       4 004] 015 [1009]
;   [Fixed  n       4 004] 018 [6011]
;   [Fixed  n       3 003] 022 [051]
;   [None   n         003] 023 [001]
;   [Fixed  n       2 002] 025 [00]
;   [Fixed  n       2 002] 026 [12]
;   [Fixed  x+n     9 009] 028 [C00000000]
;   [Fixed  x+n     9 009] 030 [C00000000]
;   [LLVAR  n    ..11 011] 032 [12400100010]
;   [LLVAR  z    ..37 037] 035 [5813390006433321D15092206440000010001]
;   [Fixed  anp    12 012] 037 [160020000000]
;   [Fixed  an      3 003] 040 [220]
;   [Fixed  ans     8 008] 041 [INT01234]
;   [Fixed  ans    15 015] 042 [INT000010001   ]
;   [Fixed  ans    40 040] 043 [468 THRESHOLD AVENUE  E5106 INT CTY ONCA]
;   [Fixed  a/n     3 003] 049 [124]
;   [Fixed  b       8 008] 052 *[742B95779B5A223F]
;   [LLLVAR n       4 004] 056 [1510]
;   [LLVAR  n    ..11 011] 100 [02006000305]
;   [LLLVAR an     15 015] 123 [511201515001002]
;   [LLVAR  ans  ..32 010] 127.002 [0000004750]
;   [Fixed  ans*   48 048] 127.003 [GI1000305SSrCINT01SSnk  028838028838II080904STtl]
;   [LLVAR  ans  ..25 003] 127.012 [RBC]
;   [Fixed  n       8 008] 127.020 [20121009]
;   [LLLLLVAans 99999 180] 127.022.Postilion:MetaData
;                                   [18Tfti:UID111211TERMINAL_ID111]
;   [LLLLLVAans 99999 180] 127.022.Tfti:UID
;                                   [<TftiUID>
;                                       <UID>TORGSLGI1000305SSr0200:028838:1009150219:006433321</UID>
;                                     </TftiUID>]
;   [LLLLLVAans 99999 180] 127.022.TERMINAL_ID
;                                   [INT01234        ]
;   [LLLLVARans .9999 0977] 127.025.IccData
;                                   [<?xml version="1.0" encoding="UTF-8"?>
;                                     <IccData>
;                                       <IccRequest>
;                                         <AmountAuthorized>000000006150</AmountAuthorized>
;                                         <AmountOther>000000000000</AmountOther>
;                                         <ApplicationInterchangeProfile>1800</ApplicationInterchangeProfile>
;                                         <ApplicationTransactionCounter>0036</ApplicationTransactionCounter>
;                                         <Cryptogram>339FA522FD7FF156</Cryptogram>
;                                         <TerminalCountryCode>124</TerminalCountryCode>
;                                         <TerminalVerificationResult>8080048000</TerminalVerificationResult>
;                                         <TransactionCurrencyCode>124</TransactionCurrencyCode>
;                                         <TransactionDate>100416</TransactionDate>
;                                         <TransactionType>01</TransactionType>
;                                         <UnpredictableNumber>7BCC0976</UnpredictableNumber>
;                                         <ApplicationIdentifier>A0000002771010</ApplicationIdentifier>
;                                         <CvmResults>020300</CvmResults>
;                                         <IssuerApplicationData>0110A000032200000000000000000000000F</IssuerApplicationData>
;                                         <TerminalCapabilities>604020</TerminalCapabilities>
;                                         <ChipConditionCode>0</ChipConditionCode>
;                                         <CryptogramInformationData>80</CryptogramInformationData>
;                                       </IccRequest>
;                                     </IccData>]
;   [LLVAR  ans  ..11 003] 127.035 [INT]
;
;binary data
;0000(0000)  30 32 30 30 f2 3e 46 d5  29 e0 91 00 00 00 00 00   0200.>F.).......
;0016(0010)  10 00 00 22 31 36 35 38  31 33 33 39 30 30 30 36   ..."165813390006
;0032(0020)  34 33 33 33 32 31 30 31  31 30 30 30 30 30 30 30   4333210110000000
;0048(0030)  30 30 30 30 36 36 36 30  31 30 30 39 31 35 30 32   0000666010091502
;0064(0040)  31 39 30 32 38 38 33 38  31 31 30 32 31 39 31 30   1902883811021910
;0080(0050)  30 39 31 35 30 39 31 30  30 39 36 30 31 31 30 35   0915091009601105
;0096(0060)  31 30 30 31 30 30 31 32  43 30 30 30 30 30 30 30   10010012C0000000
;0112(0070)  30 43 30 30 30 30 30 30  30 30 31 31 31 32 34 30   0C00000000111240
;0128(0080)  30 31 30 30 30 31 30 33  37 35 38 31 33 33 39 30   0100010375813390
;0144(0090)  30 30 36 34 33 33 33 32  31 44 31 35 30 39 32 32   006433321D150922
;0160(00a0)  30 36 34 34 30 30 30 30  30 31 30 30 30 31 31 36   0644000001000116
;0176(00b0)  30 30 32 30 30 30 30 30  30 30 32 32 30 49 4e 54   0020000000220INT
;0192(00c0)  30 31 32 33 34 49 4e 54  30 30 30 30 31 30 30 30   01234INT00001000
;0208(00d0)  31 20 20 20 34 36 38 20  54 48 52 45 53 48 4f 4c   1   468 THRESHOL
;0224(00e0)  44 20 41 56 45 4e 55 45  20 20 45 35 31 30 36 20   D AVENUE  E5106
;0240(00f0)  49 4e 54 20 43 54 59 20  4f 4e 43 41 31 32 34 74   INT CTY ONCA124t
;0256(0100)  2b 95 77 9b 5a 22 3f 30  30 34 31 35 31 30 31 31   +.w.Z"?004151011
;0272(0110)  30 32 30 30 36 30 30 30  33 30 35 30 31 35 35 31   0200600030501551
;0288(0120)  31 32 30 31 35 31 35 30  30 31 30 30 32 30 30 31   1201515001002001
;0304(0130)  32 35 32 60 10 14 80 20  00 00 00 31 30 30 30 30   252`... ...10000
;0320(0140)  30 30 30 34 37 35 30 47  49 31 30 30 30 33 30 35   0004750GI1000305
;0336(0150)  53 53 72 43 49 4e 54 30  31 53 53 6e 6b 20 20 30   SSrCINT01SSnk  0
;0352(0160)  32 38 38 33 38 30 32 38  38 33 38 49 49 30 38 30   28838028838II080
;0368(0170)  39 30 34 53 54 74 6c 30  33 52 42 43 32 30 31 32   904STtl03RBC2012
;0384(0180)  31 30 30 39 30 30 31 38  30 32 31 38 50 6f 73 74   100900180218Post
;0400(0190)  69 6c 69 6f 6e 3a 4d 65  74 61 44 61 74 61 32 33   ilion:MetaData23
;0416(01a0)  30 31 38 54 66 74 69 3a  55 49 44 31 31 31 32 31   018Tfti:UID11121
;0432(01b0)  31 54 45 52 4d 49 4e 41  4c 5f 49 44 31 31 31 31   1TERMINAL_ID1111
;0448(01c0)  38 54 66 74 69 3a 55 49  44 32 38 30 3c 54 66 74   8Tfti:UID280<Tft
;0464(01d0)  69 55 49 44 3e 3c 55 49  44 3e 54 4f 52 47 53 4c   iUID><UID>TORGSL
;0480(01e0)  47 49 31 30 30 30 33 30  35 53 53 72 30 32 30 30   GI1000305SSr0200
;0496(01f0)  3a 30 32 38 38 33 38 3a  31 30 30 39 31 35 30 32   :028838:10091502
;0512(0200)  31 39 3a 30 30 36 34 33  33 33 32 31 3c 2f 55 49   19:006433321</UI
;0528(0210)  44 3e 3c 2f 54 66 74 69  55 49 44 3e 32 31 31 54   D></TftiUID>211T
;0544(0220)  45 52 4d 49 4e 41 4c 5f  49 44 32 31 36 49 4e 54   ERMINAL_ID216INT
;0560(0230)  30 31 32 33 34 20 20 20  20 20 20 20 20 30 39 37   01234        097
;0576(0240)  37 31 37 49 63 63 44 61  74 61 33 39 36 34 3c 3f   717IccData3964<?
;0592(0250)  78 6d 6c 20 76 65 72 73  69 6f 6e 3d 22 31 2e 30   xml version="1.0
;0608(0260)  22 20 65 6e 63 6f 64 69  6e 67 3d 22 55 54 46 2d   " encoding="UTF-
;0624(0270)  38 22 3f 3e 3c 49 63 63  44 61 74 61 3e 3c 49 63   8"?><IccData><Ic
;0640(0280)  63 52 65 71 75 65 73 74  3e 3c 41 6d 6f 75 6e 74   cRequest><Amount
;0656(0290)  41 75 74 68 6f 72 69 7a  65 64 3e 30 30 30 30 30   Authorized>00000
;0672(02a0)  30 30 30 36 31 35 30 3c  2f 41 6d 6f 75 6e 74 41   0006150</AmountA
;0688(02b0)  75 74 68 6f 72 69 7a 65  64 3e 3c 41 6d 6f 75 6e   uthorized><Amoun
;0704(02c0)  74 4f 74 68 65 72 3e 30  30 30 30 30 30 30 30 30   tOther>000000000
;0720(02d0)  30 30 30 3c 2f 41 6d 6f  75 6e 74 4f 74 68 65 72   000</AmountOther
;0736(02e0)  3e 3c 41 70 70 6c 69 63  61 74 69 6f 6e 49 6e 74   ><ApplicationInt
;0752(02f0)  65 72 63 68 61 6e 67 65  50 72 6f 66 69 6c 65 3e   erchangeProfile>
;0768(0300)  31 38 30 30 3c 2f 41 70  70 6c 69 63 61 74 69 6f   1800</Applicatio
;0784(0310)  6e 49 6e 74 65 72 63 68  61 6e 67 65 50 72 6f 66   nInterchangeProf
;0800(0320)  69 6c 65 3e 3c 41 70 70  6c 69 63 61 74 69 6f 6e   ile><Application
;0816(0330)  54 72 61 6e 73 61 63 74  69 6f 6e 43 6f 75 6e 74   TransactionCount
;0832(0340)  65 72 3e 30 30 33 36 3c  2f 41 70 70 6c 69 63 61   er>0036</Applica
;0848(0350)  74 69 6f 6e 54 72 61 6e  73 61 63 74 69 6f 6e 43   tionTransactionC
;0864(0360)  6f 75 6e 74 65 72 3e 3c  43 72 79 70 74 6f 67 72   ounter><Cryptogr
;0880(0370)  61 6d 3e 33 33 39 46 41  35 32 32 46 44 37 46 46   am>339FA522FD7FF
;0896(0380)  31 35 36 3c 2f 43 72 79  70 74 6f 67 72 61 6d 3e   156</Cryptogram>
;0912(0390)  3c 54 65 72 6d 69 6e 61  6c 43 6f 75 6e 74 72 79   <TerminalCountry
;0928(03a0)  43 6f 64 65 3e 31 32 34  3c 2f 54 65 72 6d 69 6e   Code>124</Termin
;0944(03b0)  61 6c 43 6f 75 6e 74 72  79 43 6f 64 65 3e 3c 54   alCountryCode><T
;0960(03c0)  65 72 6d 69 6e 61 6c 56  65 72 69 66 69 63 61 74   erminalVerificat
;0976(03d0)  69 6f 6e 52 65 73 75 6c  74 3e 38 30 38 30 30 34   ionResult>808004
;0992(03e0)  38 30 30 30 3c 2f 54 65  72 6d 69 6e 61 6c 56 65   8000</TerminalVe
;1008(03f0)  72 69 66 69 63 61 74 69  6f 6e 52 65 73 75 6c 74   rificationResult
;1024(0400)  3e 3c 54 72 61 6e 73 61  63 74 69 6f 6e 43 75 72   ><TransactionCur
;1040(0410)  72 65 6e 63 79 43 6f 64  65 3e 31 32 34 3c 2f 54   rencyCode>124</T
;1056(0420)  72 61 6e 73 61 63 74 69  6f 6e 43 75 72 72 65 6e   ransactionCurren
;1072(0430)  63 79 43 6f 64 65 3e 3c  54 72 61 6e 73 61 63 74   cyCode><Transact
;1088(0440)  69 6f 6e 44 61 74 65 3e  31 30 30 34 31 36 3c 2f   ionDate>100416</
;1104(0450)  54 72 61 6e 73 61 63 74  69 6f 6e 44 61 74 65 3e   TransactionDate>
;1120(0460)  3c 54 72 61 6e 73 61 63  74 69 6f 6e 54 79 70 65   <TransactionType
;1136(0470)  3e 30 31 3c 2f 54 72 61  6e 73 61 63 74 69 6f 6e   >01</Transaction
;1152(0480)  54 79 70 65 3e 3c 55 6e  70 72 65 64 69 63 74 61   Type><Unpredicta
;1168(0490)  62 6c 65 4e 75 6d 62 65  72 3e 37 42 43 43 30 39   bleNumber>7BCC09
;1184(04a0)  37 36 3c 2f 55 6e 70 72  65 64 69 63 74 61 62 6c   76</Unpredictabl
;1200(04b0)  65 4e 75 6d 62 65 72 3e  3c 41 70 70 6c 69 63 61   eNumber><Applica
;1216(04c0)  74 69 6f 6e 49 64 65 6e  74 69 66 69 65 72 3e 41   tionIdentifier>A
;1232(04d0)  30 30 30 30 30 30 32 37  37 31 30 31 30 3c 2f 41   0000002771010</A
;1248(04e0)  70 70 6c 69 63 61 74 69  6f 6e 49 64 65 6e 74 69   pplicationIdenti
;1264(04f0)  66 69 65 72 3e 3c 43 76  6d 52 65 73 75 6c 74 73   fier><CvmResults
;1280(0500)  3e 30 32 30 33 30 30 3c  2f 43 76 6d 52 65 73 75   >020300</CvmResu
;1296(0510)  6c 74 73 3e 3c 49 73 73  75 65 72 41 70 70 6c 69   lts><IssuerAppli
;1312(0520)  63 61 74 69 6f 6e 44 61  74 61 3e 30 31 31 30 41   cationData>0110A
;1328(0530)  30 30 30 30 33 32 32 30  30 30 30 30 30 30 30 30   0000322000000000
;1344(0540)  30 30 30 30 30 30 30 30  30 30 30 30 30 30 46 3c   00000000000000F<
;1360(0550)  2f 49 73 73 75 65 72 41  70 70 6c 69 63 61 74 69   /IssuerApplicati
;1376(0560)  6f 6e 44 61 74 61 3e 3c  54 65 72 6d 69 6e 61 6c   onData><Terminal
;1392(0570)  43 61 70 61 62 69 6c 69  74 69 65 73 3e 36 30 34   Capabilities>604
;1408(0580)  30 32 30 3c 2f 54 65 72  6d 69 6e 61 6c 43 61 70   020</TerminalCap
;1424(0590)  61 62 69 6c 69 74 69 65  73 3e 3c 43 68 69 70 43   abilities><ChipC
;1440(05a0)  6f 6e 64 69 74 69 6f 6e  43 6f 64 65 3e 30 3c 2f   onditionCode>0</
;1456(05b0)  43 68 69 70 43 6f 6e 64  69 74 69 6f 6e 43 6f 64   ChipConditionCod
;1472(05c0)  65 3e 3c 43 72 79 70 74  6f 67 72 61 6d 49 6e 66   e><CryptogramInf
;1488(05d0)  6f 72 6d 61 74 69 6f 6e  44 61 74 61 3e 38 30 3c   ormationData>80<
;1504(05e0)  2f 43 72 79 70 74 6f 67  72 61 6d 49 6e 66 6f 72   /CryptogramInfor
;1520(05f0)  6d 61 74 69 6f 6e 44 61  74 61 3e 3c 2f 49 63 63   mationData></Icc
;1536(0600)  52 65 71 75 65 73 74 3e  3c 2f 49 63 63 44 61 74   Request></IccDat
;1552(0610)  61 3e 30 33 49 4e 54                               a>03INT