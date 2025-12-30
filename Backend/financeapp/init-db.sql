-- init-db.sql
-- Script d'initialisation pour PostgreSQL

-- Créer un schéma séparé
CREATE SCHEMA IF NOT EXISTS finance;
SET search_path TO finance;

-- Extension pour UUID si nécessaire
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Les tables seront créées par Hibernate
-- Ce fichier peut contenir des données de référence