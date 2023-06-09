PGDMP     3                    {           billing    14.5    14.5 &    !           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            "           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            #           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            $           1262    24854    billing    DATABASE     k   CREATE DATABASE billing WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE billing;
                postgres    false            �            1259    24887    account    TABLE     o   CREATE TABLE public.account (
    accountid character varying(50) NOT NULL,
    contractid integer NOT NULL
);
    DROP TABLE public.account;
       public         heap    postgres    false            �            1259    24864    cdr    TABLE     �   CREATE TABLE public.cdr (
    imsi character varying(50) NOT NULL,
    msisdna character varying(100) NOT NULL,
    msisdnb character varying(100) NOT NULL,
    startat text,
    endat text,
    duration text,
    sncode integer
);
    DROP TABLE public.cdr;
       public         heap    postgres    false            �            1259    24957 	   linktable    TABLE     �   CREATE TABLE public.linktable (
    tmcode integer,
    sncode integer,
    customerid text NOT NULL,
    accessfees integer,
    eventfees integer
);
    DROP TABLE public.linktable;
       public         heap    postgres    false            �            1259    24856    rateplan    TABLE     n   CREATE TABLE public.rateplan (
    tmcode integer NOT NULL,
    destination character varying(50) NOT NULL
);
    DROP TABLE public.rateplan;
       public         heap    postgres    false            �            1259    24855    rateplan_tmcode_seq    SEQUENCE     �   CREATE SEQUENCE public.rateplan_tmcode_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.rateplan_tmcode_seq;
       public          postgres    false    210            %           0    0    rateplan_tmcode_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.rateplan_tmcode_seq OWNED BY public.rateplan.tmcode;
          public          postgres    false    209            �            1259    24923    rtx    TABLE     �   CREATE TABLE public.rtx (
    imsi character varying(50) NOT NULL,
    voiceusage text,
    datausage text,
    smsusage text,
    extrafees text,
    tmcode integer
);
    DROP TABLE public.rtx;
       public         heap    postgres    false            �            1259    24904    services    TABLE     �   CREATE TABLE public.services (
    sncode integer NOT NULL,
    description text,
    tmcode integer,
    freeunits text,
    extraunits text
);
    DROP TABLE public.services;
       public         heap    postgres    false            p           2604    24859    rateplan tmcode    DEFAULT     r   ALTER TABLE ONLY public.rateplan ALTER COLUMN tmcode SET DEFAULT nextval('public.rateplan_tmcode_seq'::regclass);
 >   ALTER TABLE public.rateplan ALTER COLUMN tmcode DROP DEFAULT;
       public          postgres    false    210    209    210                      0    24887    account 
   TABLE DATA           8   COPY public.account (accountid, contractid) FROM stdin;
    public          postgres    false    212   �+                 0    24864    cdr 
   TABLE DATA           W   COPY public.cdr (imsi, msisdna, msisdnb, startat, endat, duration, sncode) FROM stdin;
    public          postgres    false    211   �+                 0    24957 	   linktable 
   TABLE DATA           V   COPY public.linktable (tmcode, sncode, customerid, accessfees, eventfees) FROM stdin;
    public          postgres    false    215   ,                 0    24856    rateplan 
   TABLE DATA           7   COPY public.rateplan (tmcode, destination) FROM stdin;
    public          postgres    false    210   ",                 0    24923    rtx 
   TABLE DATA           W   COPY public.rtx (imsi, voiceusage, datausage, smsusage, extrafees, tmcode) FROM stdin;
    public          postgres    false    214   ?,                 0    24904    services 
   TABLE DATA           V   COPY public.services (sncode, description, tmcode, freeunits, extraunits) FROM stdin;
    public          postgres    false    213   \,       &           0    0    rateplan_tmcode_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.rateplan_tmcode_seq', 1, false);
          public          postgres    false    209            |           2606    24891    account account_accountid_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_accountid_key UNIQUE (accountid);
 G   ALTER TABLE ONLY public.account DROP CONSTRAINT account_accountid_key;
       public            postgres    false    212            ~           2606    24893    account account_contractid_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_contractid_key UNIQUE (contractid);
 H   ALTER TABLE ONLY public.account DROP CONSTRAINT account_contractid_key;
       public            postgres    false    212            v           2606    24870    cdr cdr_msisdna_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_msisdna_key UNIQUE (msisdna);
 =   ALTER TABLE ONLY public.cdr DROP CONSTRAINT cdr_msisdna_key;
       public            postgres    false    211            x           2606    24872    cdr cdr_msisdnb_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_msisdnb_key UNIQUE (msisdnb);
 =   ALTER TABLE ONLY public.cdr DROP CONSTRAINT cdr_msisdnb_key;
       public            postgres    false    211            z           2606    24868    cdr cdr_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_pkey PRIMARY KEY (imsi);
 6   ALTER TABLE ONLY public.cdr DROP CONSTRAINT cdr_pkey;
       public            postgres    false    211            �           2606    24963    linktable linktable_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_pkey PRIMARY KEY (customerid);
 B   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_pkey;
       public            postgres    false    215            r           2606    24863 !   rateplan rateplan_destination_key 
   CONSTRAINT     c   ALTER TABLE ONLY public.rateplan
    ADD CONSTRAINT rateplan_destination_key UNIQUE (destination);
 K   ALTER TABLE ONLY public.rateplan DROP CONSTRAINT rateplan_destination_key;
       public            postgres    false    210            t           2606    24861    rateplan rateplan_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.rateplan
    ADD CONSTRAINT rateplan_pkey PRIMARY KEY (tmcode);
 @   ALTER TABLE ONLY public.rateplan DROP CONSTRAINT rateplan_pkey;
       public            postgres    false    210            �           2606    24929    rtx rtx_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.rtx
    ADD CONSTRAINT rtx_pkey PRIMARY KEY (imsi);
 6   ALTER TABLE ONLY public.rtx DROP CONSTRAINT rtx_pkey;
       public            postgres    false    214            �           2606    24910    services services_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (sncode);
 @   ALTER TABLE ONLY public.services DROP CONSTRAINT services_pkey;
       public            postgres    false    213            �           2606    24894    account account_accountid_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_accountid_fkey FOREIGN KEY (accountid) REFERENCES public.cdr(imsi);
 H   ALTER TABLE ONLY public.account DROP CONSTRAINT account_accountid_fkey;
       public          postgres    false    3194    211    212            �           2606    24899    account account_contractid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_contractid_fkey FOREIGN KEY (contractid) REFERENCES public.rateplan(tmcode);
 I   ALTER TABLE ONLY public.account DROP CONSTRAINT account_contractid_fkey;
       public          postgres    false    210    3188    212            �           2606    24974 #   linktable linktable_customerid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_customerid_fkey FOREIGN KEY (customerid) REFERENCES public.cdr(imsi);
 M   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_customerid_fkey;
       public          postgres    false    211    3194    215            �           2606    24969    linktable linktable_sncode_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_sncode_fkey FOREIGN KEY (sncode) REFERENCES public.services(sncode);
 I   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_sncode_fkey;
       public          postgres    false    213    215    3200            �           2606    24964    linktable linktable_tmcode_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.linktable
    ADD CONSTRAINT linktable_tmcode_fkey FOREIGN KEY (tmcode) REFERENCES public.rateplan(tmcode);
 I   ALTER TABLE ONLY public.linktable DROP CONSTRAINT linktable_tmcode_fkey;
       public          postgres    false    215    3188    210            �           2606    24930    rtx rtx_imsi_fkey    FK CONSTRAINT     m   ALTER TABLE ONLY public.rtx
    ADD CONSTRAINT rtx_imsi_fkey FOREIGN KEY (imsi) REFERENCES public.cdr(imsi);
 ;   ALTER TABLE ONLY public.rtx DROP CONSTRAINT rtx_imsi_fkey;
       public          postgres    false    214    3194    211            �           2606    24935    rtx rtx_tmcode_fkey    FK CONSTRAINT     x   ALTER TABLE ONLY public.rtx
    ADD CONSTRAINT rtx_tmcode_fkey FOREIGN KEY (tmcode) REFERENCES public.rateplan(tmcode);
 =   ALTER TABLE ONLY public.rtx DROP CONSTRAINT rtx_tmcode_fkey;
       public          postgres    false    3188    210    214            �           2606    24911    services services_tmcode_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_tmcode_fkey FOREIGN KEY (tmcode) REFERENCES public.rateplan(tmcode);
 G   ALTER TABLE ONLY public.services DROP CONSTRAINT services_tmcode_fkey;
       public          postgres    false    210    213    3188                  x������ � �            x������ � �            x������ � �            x������ � �            x������ � �            x������ � �     