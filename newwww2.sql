PGDMP     .                    {           newbill    14.5    14.5 "               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            !           1262    24989    newbill    DATABASE     k   CREATE DATABASE newbill WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE newbill;
                postgres    false            �            1259    25018    account    TABLE     �   CREATE TABLE public.account (
    accountid character varying(50) NOT NULL,
    contractid integer NOT NULL,
    address text,
    name text,
    msisdn text,
    email text
);
    DROP TABLE public.account;
       public         heap    postgres    false            �            1259    25025    cdr    TABLE     �   CREATE TABLE public.cdr (
    imsi character varying(50) NOT NULL,
    msisdn character varying(100) NOT NULL,
    destination character varying(100) NOT NULL,
    start_call text,
    end_call text,
    duration integer,
    sncode text
);
    DROP TABLE public.cdr;
       public         heap    postgres    false            �            1259    25036 	   linktable    TABLE     �   CREATE TABLE public.linktable (
    tmcode integer,
    sncode text,
    customerid text NOT NULL,
    accessfees integer,
    eventfees integer
);
    DROP TABLE public.linktable;
       public         heap    postgres    false            �            1259    25043    rateplan    TABLE     n   CREATE TABLE public.rateplan (
    tmcode integer NOT NULL,
    destination character varying(50) NOT NULL
);
    DROP TABLE public.rateplan;
       public         heap    postgres    false            �            1259    25050    rtx    TABLE     �   CREATE TABLE public.rtx (
    imsi character varying(50) NOT NULL,
    voiceusage integer,
    datausage integer,
    smsusage integer,
    extrafees integer,
    tmcode integer
);
    DROP TABLE public.rtx;
       public         heap    postgres    false            �            1259    25055    services    TABLE     �   CREATE TABLE public.services (
    sncode text NOT NULL,
    description text,
    tmcode integer,
    freeunits integer,
    extraunits integer
);
    DROP TABLE public.services;
       public         heap    postgres    false                      0    25018    account 
   TABLE DATA           V   COPY public.account (accountid, contractid, address, name, msisdn, email) FROM stdin;
    public          postgres    false    209   �'                 0    25025    cdr 
   TABLE DATA           `   COPY public.cdr (imsi, msisdn, destination, start_call, end_call, duration, sncode) FROM stdin;
    public          postgres    false    210   E(                 0    25036 	   linktable 
   TABLE DATA           V   COPY public.linktable (tmcode, sncode, customerid, accessfees, eventfees) FROM stdin;
    public          postgres    false    211   �(                 0    25043    rateplan 
   TABLE DATA           7   COPY public.rateplan (tmcode, destination) FROM stdin;
    public          postgres    false    212   �(                 0    25050    rtx 
   TABLE DATA           W   COPY public.rtx (imsi, voiceusage, datausage, smsusage, extrafees, tmcode) FROM stdin;
    public          postgres    false    213   �(                 0    25055    services 
   TABLE DATA           V   COPY public.services (sncode, description, tmcode, freeunits, extraunits) FROM stdin;
    public          postgres    false    214   )       p           2606    25022    account account_accountid_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_accountid_key UNIQUE (accountid);
 G   ALTER TABLE ONLY public.account DROP CONSTRAINT account_accountid_key;
       public            postgres    false    209            r           2606    25024    account account_contractid_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_contractid_key UNIQUE (contractid);
 H   ALTER TABLE ONLY public.account DROP CONSTRAINT account_contractid_key;
       public            postgres    false    209            t           2606    25033    cdr cdr_msisdna_key 
   CONSTRAINT     P   ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_msisdna_key UNIQUE (msisdn);
 =   ALTER TABLE ONLY public.cdr DROP CONSTRAINT cdr_msisdna_key;
       public            postgres    false    210            v           2606    25035    cdr cdr_msisdnb_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_msisdnb_key UNIQUE (destination);
 =   ALTER TABLE ONLY public.cdr DROP CONSTRAINT cdr_msisdnb_key;
       public            postgres    false    210            x           2606    25031    cdr cdr_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_pkey PRIMARY KEY (imsi);
 6   ALTER TABLE ONLY public.cdr DROP CONSTRAINT cdr_pkey;
       public            postgres    false    210            z           2606    25042    linktable linktable_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_pkey PRIMARY KEY (customerid);
 B   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_pkey;
       public            postgres    false    211            |           2606    25049 !   rateplan rateplan_destination_key 
   CONSTRAINT     c   ALTER TABLE ONLY public.rateplan
    ADD CONSTRAINT rateplan_destination_key UNIQUE (destination);
 K   ALTER TABLE ONLY public.rateplan DROP CONSTRAINT rateplan_destination_key;
       public            postgres    false    212            ~           2606    25047    rateplan rateplan_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.rateplan
    ADD CONSTRAINT rateplan_pkey PRIMARY KEY (tmcode);
 @   ALTER TABLE ONLY public.rateplan DROP CONSTRAINT rateplan_pkey;
       public            postgres    false    212            �           2606    25054    rtx rtx_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.rtx
    ADD CONSTRAINT rtx_pkey PRIMARY KEY (imsi);
 6   ALTER TABLE ONLY public.rtx DROP CONSTRAINT rtx_pkey;
       public            postgres    false    213            �           2606    25061    services services_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (sncode);
 @   ALTER TABLE ONLY public.services DROP CONSTRAINT services_pkey;
       public            postgres    false    214            �           2606    25062    account account_accountid_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_accountid_fkey FOREIGN KEY (accountid) REFERENCES public.cdr(imsi);
 H   ALTER TABLE ONLY public.account DROP CONSTRAINT account_accountid_fkey;
       public          postgres    false    209    210    3192            �           2606    25067    account account_contractid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_contractid_fkey FOREIGN KEY (contractid) REFERENCES public.rateplan(tmcode);
 I   ALTER TABLE ONLY public.account DROP CONSTRAINT account_contractid_fkey;
       public          postgres    false    209    212    3198            �           2606    25072 #   linktable linktable_customerid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_customerid_fkey FOREIGN KEY (customerid) REFERENCES public.cdr(imsi);
 M   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_customerid_fkey;
       public          postgres    false    210    3192    211            �           2606    25077    linktable linktable_sncode_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_sncode_fkey FOREIGN KEY (sncode) REFERENCES public.services(sncode);
 I   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_sncode_fkey;
       public          postgres    false    211    214    3202            �           2606    25082    linktable linktable_tmcode_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_tmcode_fkey FOREIGN KEY (tmcode) REFERENCES public.rateplan(tmcode);
 I   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_tmcode_fkey;
       public          postgres    false    212    3198    211            �           2606    25087    rtx rtx_imsi_fkey    FK CONSTRAINT     m   ALTER TABLE ONLY public.rtx
    ADD CONSTRAINT rtx_imsi_fkey FOREIGN KEY (imsi) REFERENCES public.cdr(imsi);
 ;   ALTER TABLE ONLY public.rtx DROP CONSTRAINT rtx_imsi_fkey;
       public          postgres    false    210    3192    213            �           2606    25092    rtx rtx_tmcode_fkey    FK CONSTRAINT     x   ALTER TABLE ONLY public.rtx
    ADD CONSTRAINT rtx_tmcode_fkey FOREIGN KEY (tmcode) REFERENCES public.rateplan(tmcode);
 =   ALTER TABLE ONLY public.rtx DROP CONSTRAINT rtx_tmcode_fkey;
       public          postgres    false    212    3198    213            �           2606    25097    services services_tmcode_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_tmcode_fkey FOREIGN KEY (tmcode) REFERENCES public.rateplan(tmcode);
 G   ALTER TABLE ONLY public.services DROP CONSTRAINT services_tmcode_fkey;
       public          postgres    false    212    3198    214               E   x�3400�4b��̢|��ʂN���D����NCC#cS3s�D��Cznbf�^r~.W� M��         0   x�3400�404426153� 2!,NC+ci"9��3�S�b���� ��	�            x�340�,��LN�44 2��W� T�0            x�340�,JM150������ E            x�3400�4bC�	��b���� 6@�         &   x�+��LN�,�
ɉ99Ŝ��&@+F��� ���     