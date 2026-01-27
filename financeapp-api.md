# FinanceApp API Documentation
Introduction
FinanceApp est une plateforme bancaire complète permettant la gestion sécurisée de transactions financières. Cette API RESTful fournit tous les endpoints nécessaires pour gérer les utilisateurs, les comptes bancaires, les transactions et l'administration.

# Version API: 1.0.0
Environnement: Production
Stack Technique: Spring Boot 3.2+, Java 17, PostgreSQL 15+, JWT, Spring Security


# 🌐 Base URL
Environnements disponibles :
Environnement	URL Base	Statut
Développement	http://localhost:8080/api	Actif
Staging	https://staging.financeapp.com/api	Maintenance
Production	https://api.financeapp.com/v1	Production

# Exemple d'URL complète :

text
GET http://localhost:8080/api/auth/me
🔐 Authentification
FinanceApp utilise l'authentification par JSON Web Tokens (JWT).

Format du Header :
text
Authorization: Bearer {votre_token_jwt}
Content-Type: application/json
Structure du Token JWT :
json
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "sub": "user@example.com",
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "role": "ROLE_USER",
    "iat": 1516239022,
    "exp": 1516242622
  }
}
Rôles disponibles :
ROLE_USER : Utilisateur standard (opérations bancaires basiques)

ROLE_BANK_MANAGER : Gestionnaire bancaire (gestion des comptes)

ROLE_ADMIN : Administrateur système (accès complet)

# ⚠️ Codes d'Erreur
Code	Signification	Description
200	OK	Requête traitée avec succès
201	Created	Ressource créée avec succès
400	Bad Request	Requête mal formée ou données invalides
401	Unauthorized	Authentification requise ou token invalide
403	Forbidden	Permissions insuffisantes
404	Not Found	Ressource non trouvée
409	Conflict	Conflit de données (ex: email déjà utilisé)
422	Unprocessable Entity	Validation métier échouée
429	Too Many Requests	Limite de requêtes dépassée
500	Internal Server Error	Erreur serveur interne
503	Service Unavailable	Service temporairement indisponible


# 🌍 Endpoints Publics
1. 🟢 Statut de l'API
Endpoint: GET /public/status

Description: Vérifie que l'API est opérationnelle

Authentification: Aucune

Taux Limite: 60 req/minute

Response:

json
{
  "status": "OK",
  "message": "FinanceApp API v1.0.0 is operational",
  "timestamp": "2024-01-15T10:30:00.000Z",
  "version": "1.0.0",
  "environment": "development",
  "uptime": "5d 3h 45m",
  "database": {
    "status": "CONNECTED",
    "type": "PostgreSQL 15.3",
    "latency": "12ms"
  },
  "services": {
    "authentication": "UP",
    "database": "UP",
    "cache": "UP",
    "message_queue": "UP"
  }
}

2. 🧪 Test Simple
Endpoint: GET /public/test

Description: Endpoint de test minimal

Authentification: Aucune

Response:

text
FinanceApp API - Ready for financial transactions
3. 👋 Hello World
Endpoint: GET /test/hello

Description: Endpoint de bienvenue

Authentification: Aucune

Response:

json
{
  "message": "Bienvenue sur FinanceApp API",
  "status": "OPERATIONAL",
  "timestamp": "2024-01-15T10:30:00.000Z",
  "endpoints": {
    "documentation": "/swagger-ui.html",
    "health": "/actuator/health",
    "metrics": "/actuator/metrics"
  },
  "version": {
    "api": "1.0.0",
    "spring_boot": "3.2.0",
    "java": "17"
  }
}
4. 🗄️ Vérification Base de Données
Endpoint: GET /test/db-check

Description: Vérifie la connexion et l'état de la base de données

Authentification: Aucune

Response:

json
{
  "database": {
    "type": "PostgreSQL",
    "version": "15.3",
    "status": "CONNECTED",
    "connection_time": "15ms",
    "pool": {
      "active": 3,
      "idle": 5,
      "total": 8
    }
  },
  "tables": [
    {
      "name": "users",
      "count": 42,
      "size": "2.4 MB"
    },
    {
      "name": "accounts",
      "count": 87,
      "size": "1.8 MB"
    },
    {
      "name": "transactions",
      "count": 1250,
      "size": "15.2 MB"
    }
  ],
  "performance": {
    "query_avg_time": "45ms",
    "transactions_per_second": 12,
    "cache_hit_ratio": 0.92
  }
}
5. 🔄 Echo Test
Endpoint: POST /test/echo

Description: Renvoie les données reçues (utile pour tester la connexion)

Authentification: Aucune

Headers:

Content-Type: application/json

Body:

json
{
  "message": "Test de connexion API",
  "timestamp": "2024-01-15T10:30:00.000Z",
  "data": {
    "test": true,
    "iteration": 1,
    "metadata": {
      "source": "client-test",
      "version": "1.0"
    }
  }
}
Response:

json
{
  "received": {
    "message": "Test de connexion API",
    "timestamp": "2024-01-15T10:30:00.000Z",
    "data": {
      "test": true,
      "iteration": 1,
      "metadata": {
        "source": "client-test",
        "version": "1.0"
      }
    }
  },
  "processed_at": "2024-01-15T10:30:00.150Z",
  "processing_time_ms": 15,
  "checksum": "a1b2c3d4e5f6",
  "server_info": {
    "instance": "financeapp-api-01",
    "region": "eu-west-3",
    "load": 0.45
  }
}
6. 📊 Health Check Étendu
Endpoint: GET /actuator/health

Description: Vérification complète de la santé de l'application

Authentification: Aucune

Response:

json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 500107862016,
        "free": 375809638400,
        "threshold": 10485760,
        "exists": true
      }
    },
    "ping": {
      "status": "UP"
    },
    "redis": {
      "status": "UP",
      "details": {
        "version": "7.0.11"
      }
    },
    "mail": {
      "status": "UP",
      "details": {
        "location": "smtp.gmail.com:587"
      }
    }
  },
  "groups": ["liveness", "readiness"]
}
7. ℹ️ Informations Système
Endpoint: GET /actuator/info

Description: Informations détaillées sur l'instance

Authentification: Aucune

Response:

json
{
  "app": {
    "name": "FinanceApp",
    "version": "1.0.0",
    "description": "Système de Gestion de Transactions Financières",
    "environment": "production"
  },
  "build": {
    "artifact": "finance-app",
    "name": "FinanceApp",
    "version": "1.0.0",
    "time": "2024-01-15T08:45:00.000Z",
    "group": "com.financeapp"
  },
  "git": {
    "branch": "main",
    "commit": {
      "id": "a1b2c3d4",
      "time": "2024-01-14T22:30:00.000Z"
    }
  },
  "java": {
    "version": "17.0.8",
    "vendor": {
      "name": "Eclipse Adoptium",
      "version": "Temurin-17.0.8+7"
    }
  },
  "os": {
    "name": "Linux",
    "version": "5.15.0",
    "arch": "amd64"
  },
  "instance": {
    "id": "financeapp-api-01",
    "startup_time": "2024-01-15T00:00:00.000Z",
    "uptime": "PT10H30M"
  }
}
🔐 Authentification
8. 📝 Inscription Utilisateur
Endpoint: POST /auth/register

Description: Crée un nouveau compte utilisateur avec un compte bancaire par défaut

Authentification: Aucune

Taux Limite: 5 req/minute par IP

Headers:

Content-Type: application/json

Body:

json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+33123456789",
  "acceptTerms": true
}
Validations:

Email: Format valide, unique

Password: Min 12 caractères, majuscule, minuscule, chiffre, spécial

Phone: Format international (optionnel)

acceptTerms: Doit être true

Response (201 Created):

json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+33123456789",
    "role": "ROLE_USER",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00.000Z",
    "emailVerified": false,
    "kycStatus": "PENDING"
  },
  "account": {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "accountNumber": "ACC1705312345678",
    "type": "CHECKING",
    "balance": 1000.00,
    "currency": "EUR",
    "status": "ACTIVE",
    "createdDate": "2024-01-15"
  },
  "metadata": {
    "welcome_bonus": 1000.00,
    "default_currency": "EUR",
    "daily_limit": 1000.00,
    "next_steps": ["VERIFY_EMAIL", "COMPLETE_KYC"]
  }
}
Erreurs possibles:

400: Validation des données échouée

409: Email déjà enregistré

422: Conditions non acceptées

9. 🔑 Connexion Utilisateur
Endpoint: POST /auth/login

Description: Authentifie un utilisateur et retourne un token JWT

Authentification: Aucune

Taux Limite: 10 req/minute par IP

Headers:

Content-Type: application/json

Body:

json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
Response (200 OK):

json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4...",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+33123456789",
    "role": "ROLE_USER",
    "status": "ACTIVE",
    "lastLogin": "2024-01-15T10:30:00.000Z",
    "preferences": {
      "language": "fr",
      "currency": "EUR",
      "notifications": {
        "email": true,
        "push": true,
        "sms": false
      }
    }
  },
  "accounts": [
    {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705312345678",
      "type": "CHECKING",
      "balance": 1250.50,
      "currency": "EUR",
      "status": "ACTIVE"
    },
    {
      "id": "660e8400-e29b-41d4-a716-446655440002",
      "accountNumber": "ACC1705312345679",
      "type": "SAVINGS",
      "balance": 5000.00,
      "currency": "EUR",
      "status": "ACTIVE"
    }
  ],
  "permissions": [
    "VIEW_ACCOUNTS",
    "CREATE_TRANSACTION",
    "VIEW_TRANSACTIONS",
    "MANAGE_PROFILE"
  ]
}
Erreurs possibles:

400: Données invalides

401: Identifiants incorrects

423: Compte verrouillé (trop de tentatives)

10. 🔄 Rafraîchissement du Token
Endpoint: POST /auth/refresh

Description: Rafraîchit un token expiré à l'aide d'un refresh token

Authentification: Refresh Token

Headers:

Authorization: Bearer {refresh_token}

Response (200 OK):

json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "nouveau_refresh_token...",
  "tokenType": "Bearer",
  "expiresIn": 86400
}
11. 👤 Profil Utilisateur
Endpoint: GET /auth/me

Description: Récupère les informations du profil utilisateur connecté

Authentification: Token JWT requis

Response (200 OK):

json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+33123456789",
  "role": "ROLE_USER",
  "status": "ACTIVE",
  "createdAt": "2024-01-10T14:30:00.000Z",
  "updatedAt": "2024-01-15T10:30:00.000Z",
  "lastLogin": "2024-01-15T10:30:00.000Z",
  "emailVerified": true,
  "phoneVerified": true,
  "kycStatus": "VERIFIED",
  "preferences": {
    "language": "fr",
    "currency": "EUR",
    "timezone": "Europe/Paris",
    "dateFormat": "DD/MM/YYYY",
    "notifications": {
      "transaction": {
        "email": true,
        "push": true,
        "sms": false
      },
      "security": {
        "email": true,
        "push": true,
        "sms": true
      },
      "marketing": {
        "email": false,
        "push": false
      }
    }
  },
  "limits": {
    "daily_transaction": 1000.00,
    "monthly_transaction": 5000.00,
    "single_transaction": 500.00
  },
  "statistics": {
    "total_transactions": 42,
    "total_volume": 12500.75,
    "favorite_category": "FOOD",
    "average_transaction": 297.64
  }
}
12. ✏️ Mise à Jour Profil
Endpoint: PUT /auth/me

Description: Met à jour les informations du profil utilisateur

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "firstName": "John",
  "lastName": "Doe Updated",
  "phone": "+33123456789",
  "preferences": {
    "language": "en",
    "currency": "USD",
    "notifications": {
      "transaction": {
        "email": true,
        "push": false
      }
    }
  }
}
Response (200 OK):

json
{
  "message": "Profil mis à jour avec succès",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "firstName": "John",
    "lastName": "Doe Updated",
    "phone": "+33123456789",
    "preferences": {
      "language": "en",
      "currency": "USD",
      "notifications": {
        "transaction": {
          "email": true,
          "push": false
        }
      }
    },
    "updatedAt": "2024-01-15T11:00:00.000Z"
  }
}
13. 🔒 Changement de Mot de Passe
Endpoint: POST /auth/change-password

Description: Permet à l'utilisateur de changer son mot de passe

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "currentPassword": "OldSecurePass123!",
  "newPassword": "NewSecurePass456!",
  "confirmPassword": "NewSecurePass456!"
}
Response (200 OK):

json
{
  "message": "Mot de passe changé avec succès",
  "timestamp": "2024-01-15T11:05:00.000Z",
  "nextLoginRequired": true,
  "securityEvent": {
    "type": "PASSWORD_CHANGE",
    "ip": "192.168.1.1",
    "userAgent": "Mozilla/5.0...",
    "location": "Paris, France"
  }
}
14. 📧 Réinitialisation Mot de Passe - Demande
Endpoint: POST /auth/forgot-password

Description: Initie le processus de réinitialisation du mot de passe

Authentification: Aucune

Taux Limite: 3 req/heure par email

Headers:

Content-Type: application/json

Body:

json
{
  "email": "john.doe@example.com"
}
Response (200 OK):

json
{
  "message": "Si l'email existe, un lien de réinitialisation a été envoyé",
  "resetTokenExpiresIn": 3600,
  "nextStep": "CHECK_EMAIL"
}
15. 🔑 Réinitialisation Mot de Passe - Confirmation
Endpoint: POST /auth/reset-password

Description: Confirme la réinitialisation du mot de passe

Authentification: Token de réinitialisation

Headers:

Content-Type: application/json

Body:

json
{
  "token": "reset_token_from_email",
  "newPassword": "BrandNewSecurePass789!",
  "confirmPassword": "BrandNewSecurePass789!"
}
Response (200 OK):

json
{
  "message": "Mot de passe réinitialisé avec succès",
  "autoLogin": true,
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
16. 🚪 Déconnexion
Endpoint: POST /auth/logout

Description: Déconnecte l'utilisateur et invalide le token

Authentification: Token JWT requis

Response (200 OK):

json
{
  "message": "Déconnexion réussie",
  "timestamp": "2024-01-15T11:10:00.000Z",
  "sessionDuration": "PT2H30M",
  "devicesLoggedOut": 1
}

# 💳 Gestion des Comptes
17. 📋 Liste des Comptes
Endpoint: GET /accounts

Description: Récupère tous les comptes de l'utilisateur connecté

Authentification: Token JWT requis

Query Parameters:

type (optionnel): CHECKING|SAVINGS|BUSINESS

status (optionnel): ACTIVE|FROZEN|CLOSED

includeBalance (optionnel): true|false (default: true)

sortBy (optionnel): balance|createdDate|type (default: createdDate)

sortOrder (optionnel): asc|desc (default: desc)

Response (200 OK):

json
{
  "accounts": [
    {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705312345678",
      "type": "CHECKING",
      "balance": 1250.50,
      "currency": "EUR",
      "status": "ACTIVE",
      "createdDate": "2024-01-10",
      "updatedAt": "2024-01-15T10:30:00.000Z",
      "overdraftLimit": 500.00,
      "dailyLimit": 1000.00,
      "monthlyLimit": 5000.00,
      "statistics": {
        "totalTransactions": 28,
        "incomingTransactions": 15,
        "outgoingTransactions": 13,
        "averageBalance": 1150.25
      },
      "metadata": {
        "primary": true,
        "nickname": "Compte Principal",
        "color": "#3B82F6"
      }
    },
    {
      "id": "660e8400-e29b-41d4-a716-446655440002",
      "accountNumber": "ACC1705312345679",
      "type": "SAVINGS",
      "balance": 5000.00,
      "currency": "EUR",
      "status": "ACTIVE",
      "createdDate": "2024-01-12",
      "interestRate": 1.5,
      "minimumBalance": 100.00,
      "metadata": {
        "primary": false,
        "nickname": "Épargne",
        "goal": 10000.00,
        "color": "#10B981"
      }
    }
  ],
  "summary": {
    "totalAccounts": 2,
    "totalBalance": 6250.50,
    "totalIncoming": 12500.75,
    "totalOutgoing": 6250.25,
    "currencies": {
      "EUR": 6250.50
    }
  },
  "pagination": {
    "total": 2,
    "page": 1,
    "perPage": 20,
    "totalPages": 1
  }
}
18. 🔍 Détails d'un Compte
Endpoint: GET /accounts/{accountId}

Description: Récupère les informations détaillées d'un compte spécifique

Authentification: Token JWT requis (doit posséder le compte)

Path Parameters:

accountId: UUID du compte

Response (200 OK):

json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "accountNumber": "ACC1705312345678",
  "iban": "FR7630001007941234567890185",
  "bic": "BDFEFRPPCCT",
  "type": "CHECKING",
  "balance": 1250.50,
  "availableBalance": 1750.50,
  "currency": "EUR",
  "status": "ACTIVE",
  "createdDate": "2024-01-10",
  "updatedAt": "2024-01-15T10:30:00.000Z",
  "overdraftLimit": 500.00,
  "dailyLimit": 1000.00,
  "monthlyLimit": 5000.00,
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
  },
  "limits": {
    "daily": {
      "remaining": 750.00,
      "used": 250.00,
      "resetAt": "2024-01-16T00:00:00.000Z"
    },
    "monthly": {
      "remaining": 3750.00,
      "used": 1250.00,
      "resetAt": "2024-02-01T00:00:00.000Z"
    }
  },
  "statistics": {
    "totalTransactions": 28,
    "incoming": {
      "count": 15,
      "amount": 12500.75
    },
    "outgoing": {
      "count": 13,
      "amount": 6250.25
    },
    "averageBalance": 1150.25,
    "balanceHistory": [
      {"date": "2024-01-10", "balance": 1000.00},
      {"date": "2024-01-11", "balance": 950.50},
      {"date": "2024-01-12", "balance": 1200.00},
      {"date": "2024-01-13", "balance": 1150.75},
      {"date": "2024-01-14", "balance": 1250.50}
    ]
  },
  "metadata": {
    "primary": true,
    "nickname": "Compte Principal",
    "color": "#3B82F6",
    "createdBy": "SYSTEM",
    "tags": ["principal", "quotidien"]
  }
}
19. ➕ Création d'un Compte
Endpoint: POST /accounts

Description: Crée un nouveau compte bancaire pour l'utilisateur

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "type": "SAVINGS",
  "currency": "EUR",
  "nickname": "Épargne Vacances",
  "initialDeposit": 100.00,
  "overdraftLimit": 0.00,
  "dailyLimit": 500.00,
  "metadata": {
    "goal": 5000.00,
    "goalDate": "2024-12-31",
    "color": "#10B981"
  }
}
Response (201 Created):

json
{
  "message": "Compte créé avec succès",
  "account": {
    "id": "660e8400-e29b-41d4-a716-446655440003",
    "accountNumber": "ACC1705312345680",
    "iban": "FR7630001007941234567890186",
    "type": "SAVINGS",
    "balance": 100.00,
    "currency": "EUR",
    "status": "ACTIVE",
    "createdDate": "2024-01-15",
    "overdraftLimit": 0.00,
    "dailyLimit": 500.00,
    "metadata": {
      "nickname": "Épargne Vacances",
      "goal": 5000.00,
      "goalDate": "2024-12-31",
      "color": "#10B981"
    }
  },
  "nextSteps": [
    "FUND_ACCOUNT",
    "SETUP_AUTO_SAVE",
    "ADD_TO_FAVORITES"
  ],
  "limits": {
    "maxAccountsPerUser": 5,
    "remainingAccounts": 3
  }
}
20. 💰 Solde du Compte
Endpoint: GET /accounts/{accountId}/balance

Description: Récupère le solde actuel d'un compte

Authentification: Token JWT requis

Response (200 OK):

json
{
  "accountId": "660e8400-e29b-41d4-a716-446655440001",
  "accountNumber": "ACC1705312345678",
  "balance": 1250.50,
  "availableBalance": 1750.50,
  "currency": "EUR",
  "lastUpdated": "2024-01-15T10:30:00.000Z",
  "holdAmount": 0.00,
  "overdraftAvailable": 500.00,
  "projectedBalance": {
    "endOfDay": 1250.50,
    "endOfWeek": 1300.00,
    "endOfMonth": 1500.00
  }
}
21. 📈 Historique des Soldes
Endpoint: GET /accounts/{accountId}/balance-history

Description: Récupère l'historique des soldes sur une période

Authentification: Token JWT requis

Query Parameters:

period (optionnel): day|week|month|year|custom (default: month)

from (optionnel): Date de début (format: YYYY-MM-DD)

to (optionnel): Date de fin (format: YYYY-MM-DD)

interval (optionnel): hourly|daily|weekly|monthly (default: daily)

Response (200 OK):

json
{
  "accountId": "660e8400-e29b-41d4-a716-446655440001",
  "period": {
    "from": "2024-01-01",
    "to": "2024-01-15",
    "interval": "daily"
  },
  "history": [
    {
      "date": "2024-01-01",
      "balance": 1000.00,
      "change": 0.00,
      "changePercent": 0.0
    },
    {
      "date": "2024-01-02",
      "balance": 950.50,
      "change": -49.50,
      "changePercent": -4.95
    },
    {
      "date": "2024-01-03",
      "balance": 1200.00,
      "change": 249.50,
      "changePercent": 26.25
    },
    // ... autres jours
    {
      "date": "2024-01-15",
      "balance": 1250.50,
      "change": 50.50,
      "changePercent": 4.22
    }
  ],
  "statistics": {
    "startingBalance": 1000.00,
    "endingBalance": 1250.50,
    "totalChange": 250.50,
    "averageBalance": 1150.25,
    "minBalance": 950.50,
    "maxBalance": 1300.00,
    "volatility": 12.5
  }
}
22. 🔧 Mise à Jour des Limites
Endpoint: PUT /accounts/{accountId}/limits

Description: Met à jour les limites d'un compte

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "dailyLimit": 2000.00,
  "monthlyLimit": 10000.00,
  "overdraftLimit": 1000.00,
  "singleTransactionLimit": 1500.00
}
Response (200 OK):

json
{
  "message": "Limites mises à jour avec succès",
  "accountId": "660e8400-e29b-41d4-a716-446655440001",
  "updatedLimits": {
    "dailyLimit": {
      "old": 1000.00,
      "new": 2000.00,
      "change": 1000.00
    },
    "overdraftLimit": {
      "old": 500.00,
      "new": 1000.00,
      "change": 500.00
    }
  },
  "requiresConfirmation": false,
  "effectiveDate": "2024-01-15T12:00:00.000Z"
}
23. 🏷️ Mise à Jour Métadonnées
Endpoint: PUT /accounts/{accountId}/metadata

Description: Met à jour les métadonnées d'un compte

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "nickname": "Compte Projets",
  "color": "#8B5CF6",
  "tags": ["projets", "travail"],
  "notes": "Compte dédié aux projets freelance",
  "isPrimary": true
}
Response (200 OK):

json
{
  "message": "Métadonnées mises à jour",
  "accountId": "660e8400-e29b-41d4-a716-446655440001",
  "metadata": {
    "nickname": "Compte Projets",
    "color": "#8B5CF6",
    "tags": ["projets", "travail"],
    "notes": "Compte dédié aux projets freelance",
    "isPrimary": true,
    "updatedAt": "2024-01-15T12:05:00.000Z"
  }
}
24. ❌ Fermeture de Compte
Endpoint: DELETE /accounts/{accountId}

Description: Ferme définitivement un compte

Authentification: Token JWT requis

Query Parameters:

transferAccountId (requis): UUID du compte pour transférer le solde

reason (optionnel): Raison de la fermeture

Response (200 OK):

json
{
  "message": "Compte fermé avec succès",
  "accountId": "660e8400-e29b-41d4-a716-446655440002",
  "closedAt": "2024-01-15T12:10:00.000Z",
  "finalBalance": 5000.00,
  "transferredTo": "660e8400-e29b-41d4-a716-446655440001",
  "transactionReference": "CLS1705312345679",
  "retentionPeriod": 365,
  "documentsAvailableUntil": "2025-01-15"
}

# 💸 Transactions Financières
25. 📋 Liste des Transactions
Endpoint: GET /transactions

Description: Récupère l'historique des transactions

Authentification: Token JWT requis

Query Parameters:

accountId (optionnel): Filtrer par compte

type (optionnel): TRANSFER|DEPOSIT|WITHDRAWAL|PAYMENT

category (optionnel): FOOD|TRANSPORT|SHOPPING|etc.

status (optionnel): PENDING|COMPLETED|FAILED|CANCELLED

fromDate (optionnel): Date de début (format: YYYY-MM-DD)

toDate (optionnel): Date de fin (format: YYYY-MM-DD)

minAmount (optionnel): Montant minimum

maxAmount (optionnel): Montant maximum

search (optionnel): Recherche dans la description

page (optionnel): Numéro de page (default: 1)

limit (optionnel): Résultats par page (default: 20, max: 100)

sortBy (optionnel): date|amount (default: date)

sortOrder (optionnel): asc|desc (default: desc)

Response (200 OK):

json
{
  "transactions": [
    {
      "id": "770e8400-e29b-41d4-a716-446655440001",
      "reference": "TXN1705312345678",
      "amount": 150.75,
      "type": "TRANSFER",
      "status": "COMPLETED",
      "description": "Restaurant Le Gourmet",
      "category": "FOOD",
      "timestamp": "2024-01-15T12:00:00.000Z",
      "fromAccount": {
        "id": "660e8400-e29b-41d4-a716-446655440001",
        "accountNumber": "ACC1705312345678",
        "user": {
          "id": "550e8400-e29b-41d4-a716-446655440000",
          "firstName": "John",
          "lastName": "Doe"
        }
      },
      "toAccount": {
        "id": "880e8400-e29b-41d4-a716-446655440001",
        "accountNumber": "ACC1705318765432",
        "user": {
          "id": "990e8400-e29b-41d4-a716-446655440000",
          "firstName": "Restaurant",
          "lastName": "Le Gourmet"
        }
      },
      "balanceAfter": 1100.00,
      "metadata": {
        "location": "Paris, France",
        "merchantId": "MCH123456",
        "terminalId": "TERM789012"
      },
      "tags": ["restaurant", "dinner"]
    },
    {
      "id": "770e8400-e29b-41d4-a716-446655440002",
      "reference": "TXN1705312345679",
      "amount": 500.00,
      "type": "DEPOSIT",
      "status": "COMPLETED",
      "description": "Virement salaire",
      "category": "SALARY",
      "timestamp": "2024-01-14T09:00:00.000Z",
      "fromAccount": null,
      "toAccount": {
        "id": "660e8400-e29b-41d4-a716-446655440001",
        "accountNumber": "ACC1705312345678",
        "user": {
          "id": "550e8400-e29b-41d4-a716-446655440000",
          "firstName": "John",
          "lastName": "Doe"
        }
      },
      "balanceAfter": 1250.50,
      "metadata": {
        "employer": "TechCorp Inc.",
        "period": "2024-01"
      }
    }
  ],
  "summary": {
    "totalTransactions": 42,
    "totalAmount": 12500.75,
    "incoming": {
      "count": 15,
      "amount": 12500.75
    },
    "outgoing": {
      "count": 27,
      "amount": 6250.25
    },
    "byCategory": {
      "FOOD": 1250.50,
      "TRANSPORT": 450.25,
      "SHOPPING": 875.00,
      "SALARY": 5000.00,
      "OTHER": 925.00
    },
    "byMonth": {
      "2024-01": 1250.50,
      "2023-12": 2400.75
    }
  },
  "pagination": {
    "total": 42,
    "page": 1,
    "perPage": 20,
    "totalPages": 3
  }
}
26. 🔍 Détails d'une Transaction
Endpoint: GET /transactions/{transactionId}

Description: Récupère les détails complets d'une transaction

Authentification: Token JWT requis

Response (200 OK):

json
{
  "id": "770e8400-e29b-41d4-a716-446655440001",
  "reference": "TXN1705312345678",
  "amount": 150.75,
  "currency": "EUR",
  "type": "TRANSFER",
  "status": "COMPLETED",
  "description": "Restaurant Le Gourmet - Dîner d'affaires",
  "category": "FOOD",
  "timestamp": "2024-01-15T12:00:00.000Z",
  "initiatedAt": "2024-01-15T11:55:00.000Z",
  "completedAt": "2024-01-15T12:00:00.000Z",
  "fromAccount": {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "accountNumber": "ACC1705312345678",
    "iban": "FR7630001007941234567890185",
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com"
    }
  },
  "toAccount": {
    "id": "880e8400-e29b-41d4-a716-446655440001",
    "accountNumber": "ACC1705318765432",
    "iban": "FR7630001007949876543210185",
    "user": {
      "id": "990e8400-e29b-41d4-a716-446655440000",
      "firstName": "Restaurant",
      "lastName": "Le Gourmet",
      "email": "contact@legourmet.com"
    }
  },
  "balanceAfter": 1100.00,
  "exchangeRate": 1.0,
  "fees": {
    "transactionFee": 0.75,
    "currencyConversionFee": 0.00,
    "totalFees": 0.75
  },
  "metadata": {
    "location": {
      "address": "12 Rue de la Paix",
      "city": "Paris",
      "country": "France",
      "latitude": 48.868,
      "longitude": 2.331
    },
    "merchant": {
      "id": "MCH123456",
      "name": "Le Gourmet",
      "category": "Restaurant",
      "mcc": "5812"
    },
    "terminal": {
      "id": "TERM789012",
      "type": "POS"
    },
    "device": {
      "type": "MOBILE",
      "os": "iOS",
      "browser": "Safari"
    },
    "authentication": {
      "method": "BIOMETRIC",
      "level": "HIGH"
    }
  },
  "auditTrail": [
    {
      "action": "CREATED",
      "timestamp": "2024-01-15T11:55:00.000Z",
      "userId": "550e8400-e29b-41d4-a716-446655440000",
      "ip": "192.168.1.1"
    },
    {
      "action": "VALIDATED",
      "timestamp": "2024-01-15T11:56:00.000Z",
      "system": "FRAUD_DETECTION",
      "score": 15
    },
    {
      "action": "EXECUTED",
      "timestamp": "2024-01-15T12:00:00.000Z",
      "system": "PAYMENT_GATEWAY",
      "reference": "PGW123456789"
    }
  ],
  "tags": ["restaurant", "dinner", "business"],
  "notes": "Dîner avec client important",
  "attachments": [
    {
      "id": "att123456",
      "name": "receipt.pdf",
      "type": "PDF",
      "size": 245760,
      "url": "/api/transactions/770e8400-e29b-41d4-a716-446655440001/attachments/att123456"
    }
  ],
  "relatedTransactions": [
    {
      "id": "770e8400-e29b-41d4-a716-446655440003",
      "reference": "TXN1705312345680",
      "amount": 25.50,
      "description": "Pourboire serveur",
      "timestamp": "2024-01-15T12:05:00.000Z"
    }
  ]
}
27. 🔄 Effectuer un Transfert
Endpoint: POST /transactions/transfer

Description: Effectue un transfert entre deux comptes

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "fromAccountId": "660e8400-e29b-41d4-a716-446655440001",
  "toAccountId": "880e8400-e29b-41d4-a716-446655440001",
  "toAccountNumber": "ACC1705318765432", // Alternative à toAccountId
  "amount": 150.75,
  "currency": "EUR",
  "description": "Restaurant Le Gourmet",
  "category": "FOOD",
  "scheduledFor": null, // Pour virements programmés
  "metadata": {
    "location": "Paris, France",
    "merchantId": "MCH123456",
    "notes": "Dîner d'affaires"
  },
  "tags": ["restaurant", "business"],
  "idempotencyKey": "unique-client-generated-key"
}
Response (201 Created):

json
{
  "message": "Transfert initié avec succès",
  "transaction": {
    "id": "770e8400-e29b-41d4-a716-446655440001",
    "reference": "TXN1705312345678",
    "amount": 150.75,
    "type": "TRANSFER",
    "status": "PENDING",
    "description": "Restaurant Le Gourmet",
    "category": "FOOD",
    "timestamp": "2024-01-15T12:00:00.000Z",
    "fromAccount": {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705312345678"
    },
    "toAccount": {
      "id": "880e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705318765432"
    },
    "estimatedCompletion": "2024-01-15T12:00:30.000Z",
    "exchangeRate": 1.0,
    "fees": {
      "transactionFee": 0.75,
      "totalFees": 0.75
    },
    "netAmount": 150.00
  },
  "validation": {
    "dailyLimit": {
      "remaining": 849.25,
      "used": 150.75,
      "limit": 1000.00
    },
    "balanceCheck": {
      "current": 1250.50,
      "after": 1099.75,
      "available": 1750.50
    },
    "fraudScore": 15,
    "riskLevel": "LOW"
  },
  "nextSteps": [
    {
      "action": "AWAIT_CONFIRMATION",
      "estimatedTime": "30s",
      "webhook": "/api/webhooks/transactions/770e8400-e29b-41d4-a716-446655440001"
    }
  ]
}
28. 💰 Effectuer un Dépôt
Endpoint: POST /transactions/deposit

Description: Effectue un dépôt sur un compte

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "toAccountId": "660e8400-e29b-41d4-a716-446655440001",
  "amount": 500.00,
  "currency": "EUR",
  "description": "Virement salaire Janvier",
  "category": "SALARY",
  "source": "EMPLOYER",
  "sourceDetails": {
    "employerName": "TechCorp Inc.",
    "period": "2024-01",
    "reference": "SALARY-2024-01"
  },
  "metadata": {
    "paymentMethod": "BANK_TRANSFER",
    "originBank": "BNP Paribas"
  }
}
Response (201 Created):

json
{
  "message": "Dépôt initié avec succès",
  "transaction": {
    "id": "770e8400-e29b-41d4-a716-446655440002",
    "reference": "TXN1705312345679",
    "amount": 500.00,
    "type": "DEPOSIT",
    "status": "PENDING",
    "description": "Virement salaire Janvier",
    "category": "SALARY",
    "timestamp": "2024-01-15T12:05:00.000Z",
    "toAccount": {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705312345678"
    },
    "estimatedCompletion": "2024-01-16T09:00:00.000Z",
    "fees": {
      "transactionFee": 0.00,
      "totalFees": 0.00
    }
  },
  "limits": {
    "dailyDepositLimit": {
      "remaining": 4500.00,
      "used": 500.00,
      "limit": 5000.00
    }
  }
}
29. 💸 Effectuer un Retrait
Endpoint: POST /transactions/withdrawal

Description: Effectue un retrait d'un compte

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "fromAccountId": "660e8400-e29b-41d4-a716-446655440001",
  "amount": 200.00,
  "currency": "EUR",
  "description": "Retrait DAB",
  "category": "CASH",
  "location": {
    "address": "15 Avenue des Champs-Élysées",
    "city": "Paris",
    "country": "France",
    "atmId": "ATM123456"
  },
  "metadata": {
    "atmNetwork": "EUROPEAN",
    "receiptRequired": true
  }
}
Response (201 Created):

json
{
  "message": "Retrait initié avec succès",
  "transaction": {
    "id": "770e8400-e29b-41d4-a716-446655440003",
    "reference": "TXN1705312345680",
    "amount": 200.00,
    "type": "WITHDRAWAL",
    "status": "COMPLETED",
    "description": "Retrait DAB - 15 Avenue des Champs-Élysées",
    "category": "CASH",
    "timestamp": "2024-01-15T12:10:00.000Z",
    "fromAccount": {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705312345678"
    },
    "balanceAfter": 900.00,
    "fees": {
      "atmFee": 2.00,
      "networkFee": 0.50,
      "totalFees": 2.50
    },
    "metadata": {
      "location": {
        "address": "15 Avenue des Champs-Élysées",
        "city": "Paris",
        "country": "France",
        "atmId": "ATM123456"
      },
      "receiptNumber": "RCPT789012"
    }
  }
}
30. ⏰ Virements Programmés
Endpoint: POST /transactions/scheduled

Description: Programme un virement récurrent ou à date future

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "fromAccountId": "660e8400-e29b-41d4-a716-446655440001",
  "toAccountId": "880e8400-e29b-41d4-a716-446655440002",
  "amount": 300.00,
  "description": "Loyer mensuel",
  "category": "RENT",
  "schedule": {
    "type": "RECURRING", // ONCE, RECURRING
    "startDate": "2024-02-01",
    "endDate": "2024-12-01",
    "frequency": "MONTHLY", // DAILY, WEEKLY, BIWEEKLY, MONTHLY, QUARTERLY, YEARLY
    "dayOfMonth": 1,
    "notifyBeforeDays": 3
  },
  "metadata": {
    "landlord": "M. Dupont",
    "property": "Appartement Paris 15ème",
    "contractReference": "CONTRACT-2024-001"
  }
}
Response (201 Created):

json
{
  "message": "Virement programmé créé",
  "scheduledTransaction": {
    "id": "sched123456",
    "reference": "SCH1705312345678",
    "amount": 300.00,
    "description": "Loyer mensuel",
    "category": "RENT",
    "status": "ACTIVE",
    "nextExecution": "2024-02-01T00:00:00.000Z",
    "schedule": {
      "type": "RECURRING",
      "frequency": "MONTHLY",
      "startDate": "2024-02-01",
      "endDate": "2024-12-01",
      "remainingExecutions": 11
    },
    "metadata": {
      "landlord": "M. Dupont",
      "property": "Appartement Paris 15ème"
    }
  },
  "upcomingExecutions": [
    {
      "date": "2024-02-01",
      "estimatedAmount": 300.00,
      "status": "PENDING"
    },
    {
      "date": "2024-03-01",
      "estimatedAmount": 300.00,
      "status": "PENDING"
    }
  ],
  "notifications": {
    "enabled": true,
    "nextReminder": "2024-01-29T00:00:00.000Z"
  }
}
31. 📅 Liste des Virements Programmés
Endpoint: GET /transactions/scheduled

Description: Liste tous les virements programmés

Authentification: Token JWT requis

Query Parameters:

status (optionnel): ACTIVE|PAUSED|CANCELLED|COMPLETED

fromDate (optionnel): Date de début

toDate (optionnel): Date de fin

Response (200 OK):

json
{
  "scheduledTransactions": [
    {
      "id": "sched123456",
      "reference": "SCH1705312345678",
      "amount": 300.00,
      "description": "Loyer mensuel",
      "category": "RENT",
      "status": "ACTIVE",
      "createdAt": "2024-01-15T12:15:00.000Z",
      "nextExecution": "2024-02-01T00:00:00.000Z",
      "lastExecution": null,
      "schedule": {
        "type": "RECURRING",
        "frequency": "MONTHLY",
        "startDate": "2024-02-01",
        "endDate": "2024-12-01"
      },
      "statistics": {
        "executed": 0,
        "failed": 0,
        "remaining": 11,
        "totalAmount": 3300.00
      }
    }
  ],
  "summary": {
    "totalActive": 3,
    "totalMonthlyAmount": 850.00,
    "nextExecution": "2024-02-01T00:00:00.000Z",
    "upcomingAmount": 850.00
  }
}
32. ⏸️ Gestion Virement Programmés
Endpoint: PUT /transactions/scheduled/{id}/status

Description: Modifie le statut d'un virement programmé

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "status": "PAUSED", // ACTIVE, PAUSED, CANCELLED
  "reason": "Vacances"
}
Response (200 OK):

json
{
  "message": "Statut du virement programmé mis à jour",
  "scheduledTransactionId": "sched123456",
  "oldStatus": "ACTIVE",
  "newStatus": "PAUSED",
  "updatedAt": "2024-01-15T12:20:00.000Z",
  "nextExecution": null,
  "resumeDate": "2024-09-01"
}
33. 🔍 Recherche de Transactions
Endpoint: GET /transactions/search

Description: Recherche avancée dans les transactions

Authentification: Token JWT requis

Query Parameters:

q (requis): Terme de recherche

fields (optionnel): description|reference|category|merchant (default: all)

fuzzy (optionnel): true|false (default: false)

Response (200 OK):

json
{
  "query": "restaurant",
  "results": [
    {
      "id": "770e8400-e29b-41d4-a716-446655440001",
      "reference": "TXN1705312345678",
      "amount": 150.75,
      "description": "Restaurant Le Gourmet",
      "category": "FOOD",
      "timestamp": "2024-01-15T12:00:00.000Z",
      "account": {
        "id": "660e8400-e29b-41d4-a716-446655440001",
        "accountNumber": "ACC1705312345678"
      },
      "highlight": {
        "description": ["<em>Restaurant</em> Le Gourmet"]
      },
      "score": 0.95
    }
  ],
  "total": 1,
  "facets": {
    "category": {
      "FOOD": 1
    },
    "month": {
      "2024-01": 1
    }
  }
}
34. 📊 Statistiques Transactions
Endpoint: GET /transactions/statistics

Description: Récupère les statistiques des transactions

Authentification: Token JWT requis

Query Parameters:

period (optionnel): week|month|quarter|year|custom (default: month)

fromDate (optionnel): Date de début

toDate (optionnel): Date de fin

groupBy (optionnel): day|week|month|category|type (default: category)

Response (200 OK):

json
{
  "period": {
    "from": "2024-01-01",
    "to": "2024-01-15"
  },
  "summary": {
    "totalTransactions": 42,
    "totalAmount": 12500.75,
    "averageTransaction": 297.64,
    "largestTransaction": 500.00,
    "smallestTransaction": 5.50
  },
  "byCategory": [
    {
      "category": "SALARY",
      "amount": 5000.00,
      "count": 1,
      "percentage": 40.0
    },
    {
      "category": "FOOD",
      "amount": 1250.50,
      "count": 8,
      "percentage": 10.0
    },
    {
      "category": "SHOPPING",
      "amount": 875.00,
      "count": 5,
      "percentage": 7.0
    }
  ],
  "byDay": [
    {
      "date": "2024-01-01",
      "amount": 0.00,
      "count": 0
    },
    {
      "date": "2024-01-02",
      "amount": 150.75,
      "count": 2
    }
  ],
  "trends": {
    "weeklyGrowth": 12.5,
    "monthlyGrowth": 25.3,
    "averageDailySpending": 83.34
  },
  "insights": [
    "Vous dépensez 15% de moins en restauration ce mois-ci",
    "Vos économies ont augmenté de 20%",
    "Votre plus grosse dépense: Électronique (450€)"
  ]
}
35. 📁 Export des Transactions
Endpoint: POST /transactions/export

Description: Exporte les transactions dans un format spécifique

Authentification: Token JWT requis

Headers:

Content-Type: application/json

Body:

json
{
  "format": "CSV", // CSV, JSON, PDF, EXCEL
  "period": {
    "from": "2024-01-01",
    "to": "2024-01-15"
  },
  "include": ["transactions", "summary", "receipts"],
  "filters": {
    "minAmount": 10.00,
    "categories": ["FOOD", "TRANSPORT"]
  }
}
Response (202 Accepted):

json
{
  "exportId": "export123456",
  "status": "PROCESSING",
  "estimatedCompletion": "2024-01-15T12:25:00.000Z",
  "downloadUrl": "/api/exports/export123456",
  "format": "CSV",
  "size": null,
  "notification": {
    "email": "john.doe@example.com",
    "webhook": "https://yourdomain.com/webhooks/export"
  }
}

# 👥 Administration
36. 📋 Liste des Utilisateurs (Admin)
Endpoint: GET /admin/users

Description: Liste tous les utilisateurs (admin seulement)

Authentification: Token JWT avec rôle ADMIN requis

Query Parameters:

status (optionnel): ACTIVE|INACTIVE|LOCKED

role (optionnel): ROLE_USER|ROLE_BANK_MANAGER|ROLE_ADMIN

verified (optionnel): true|false

fromDate (optionnel): Date d'inscription de début

toDate (optionnel): Date d'inscription de fin

search (optionnel): Recherche email/nom

page (optionnel): Numéro de page

limit (optionnel): Résultats par page (default: 20)

Response (200 OK):

json
{
  "users": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "email": "john.doe@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phone": "+33123456789",
      "role": "ROLE_USER",
      "status": "ACTIVE",
      "createdAt": "2024-01-10T14:30:00.000Z",
      "lastLogin": "2024-01-15T10:30:00.000Z",
      "emailVerified": true,
      "phoneVerified": true,
      "kycStatus": "VERIFIED",
      "statistics": {
        "totalAccounts": 2,
        "totalBalance": 6250.50,
        "totalTransactions": 42,
        "lastTransaction": "2024-01-15T12:00:00.000Z"
      },
      "flags": {
        "suspiciousActivity": false,
        "highValue": false,
        "newCustomer": true
      }
    }
  ],
  "summary": {
    "totalUsers": 150,
    "activeUsers": 142,
    "newUsersToday": 3,
    "lockedUsers": 2,
    "byRole": {
      "ROLE_USER": 145,
      "ROLE_BANK_MANAGER": 3,
      "ROLE_ADMIN": 2
    }
  },
  "pagination": {
    "total": 150,
    "page": 1,
    "perPage": 20,
    "totalPages": 8
  }
}
37. 🔧 Changer Statut Utilisateur (Admin)
Endpoint: PUT /admin/users/{userId}/status

Description: Modifie le statut d'un utilisateur

Authentification: Token JWT avec rôle ADMIN requis

Headers:

Content-Type: application/json

Body:

json
{
  "status": "LOCKED",
  "reason": "Activité suspecte détectée",
  "notes": "Multiples tentatives de connexion échouées",
  "duration": "24h", // Optionnel: 1h, 24h, 7d, permanent
  "notifyUser": true
}
Response (200 OK):

json
{
  "message": "Statut utilisateur mis à jour",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "oldStatus": "ACTIVE",
  "newStatus": "LOCKED",
  "reason": "Activité suspecte détectée",
  "lockedUntil": "2024-01-16T12:30:00.000Z",
  "updatedBy": "admin@financeapp.com",
  "timestamp": "2024-01-15T12:30:00.000Z",
  "notificationSent": true
}
38. 👑 Changer Rôle Utilisateur (Admin)
Endpoint: PUT /admin/users/{userId}/role

Description: Modifie le rôle d'un utilisateur

Authentification: Token JWT avec rôle ADMIN requis

Headers:

Content-Type: application/json

Body:

json
{
  "role": "ROLE_BANK_MANAGER",
  "reason": "Promotion interne",
  "permissions": ["VIEW_ALL_ACCOUNTS", "APPROVE_TRANSACTIONS"],
  "effectiveDate": "2024-01-16"
}
Response (200 OK):

json
{
  "message": "Rôle utilisateur mis à jour",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "oldRole": "ROLE_USER",
  "newRole": "ROLE_BANK_MANAGER",
  "permissionsGranted": ["VIEW_ALL_ACCOUNTS", "APPROVE_TRANSACTIONS"],
  "effectiveDate": "2024-01-16",
  "updatedBy": "admin@financeapp.com",
  "timestamp": "2024-01-15T12:35:00.000Z"
}
39. 🔍 Audit Utilisateur (Admin)
Endpoint: GET /admin/users/{userId}/audit

Description: Récupère l'historique d'audit d'un utilisateur

Authentification: Token JWT avec rôle ADMIN requis

Query Parameters:

action (optionnel): Filtrer par type d'action

fromDate (optionnel): Date de début

toDate (optionnel): Date de fin

limit (optionnel): Nombre maximum d'entrées (default: 100)

Response (200 OK):

json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "auditLogs": [
    {
      "id": "audit123456",
      "action": "LOGIN",
      "timestamp": "2024-01-15T10:30:00.000Z",
      "ip": "192.168.1.1",
      "userAgent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)",
      "location": "Paris, France",
      "status": "SUCCESS",
      "details": {
        "method": "PASSWORD",
        "device": "KNOWN_DEVICE"
      }
    },
    {
      "id": "audit123457",
      "action": "TRANSACTION_CREATED",
      "timestamp": "2024-01-15T12:00:00.000Z",
      "ip": "192.168.1.1",
      "status": "SUCCESS",
      "details": {
        "transactionId": "770e8400-e29b-41d4-a716-446655440001",
        "amount": 150.75,
        "type": "TRANSFER"
      }
    }
  ],
  "summary": {
    "totalLogs": 42,
    "successfulActions": 40,
    "failedActions": 2,
    "mostCommonAction": "LOGIN",
    "lastFailedLogin": "2024-01-14T22:15:00.000Z"
  }
}
40. 🚨 Alertes Suspicion (Admin)
Endpoint: GET /admin/alerts

Description: Récupère les alertes de suspicion d'activité frauduleuse

Authentification: Token JWT avec rôle ADMIN requis

Query Parameters:

severity (optionnel): LOW|MEDIUM|HIGH|CRITICAL

status (optionnel): NEW|IN_REVIEW|RESOLVED|DISMISSED

type (optionnel): FRAUD|SECURITY|COMPLIANCE|OPERATIONAL

Response (200 OK):

json
{
  "alerts": [
    {
      "id": "alert123456",
      "type": "FRAUD",
      "severity": "HIGH",
      "status": "NEW",
      "title": "Transaction suspecte détectée",
      "description": "Transaction de 5000€ vers un compte à l'étranger",
      "userId": "550e8400-e29b-41d4-a716-446655440000",
      "transactionId": "770e8400-e29b-41d4-a716-446655440005",
      "detectedAt": "2024-01-15T11:45:00.000Z",
      "riskScore": 85,
      "indicators": ["LARGE_AMOUNT", "FOREIGN_COUNTRY", "UNUSUAL_TIME"],
      "actions": ["BLOCK_TRANSACTION", "NOTIFY_USER", "REVIEW_MANUALLY"]
    }
  ],
  "summary": {
    "totalAlerts": 5,
    "newAlerts": 2,
    "highSeverity": 1,
    "byType": {
      "FRAUD": 3,
      "SECURITY": 1,
      "COMPLIANCE": 1
    }
  },
  "dashboard": {
    "fraudAttemptsToday": 3,
    "blockedTransactions": 2,
    "averageResponseTime": "15m",
    "falsePositiveRate": 0.05
  }
}
41. ✅ Approbation Transaction (Manager)
Endpoint: POST /admin/transactions/{transactionId}/approve

Description: Approuve une transaction nécessitant une validation manuelle

Authentification: Token JWT avec rôle BANK_MANAGER ou ADMIN requis

Headers:

Content-Type: application/json

Body:

json
{
  "approved": true,
  "notes": "Transaction validée après vérification KYC",
  "overrideLimits": false,
  "nextReviewDate": null
}
Response (200 OK):

json
{
  "message": "Transaction approuvée",
  "transactionId": "770e8400-e29b-41d4-a716-446655440005",
  "status": "APPROVED",
  "approvedBy": "manager@financeapp.com",
  "approvedAt": "2024-01-15T12:40:00.000Z",
  "notes": "Transaction validée après vérification KYC",
  "overrideLimits": false,
  "nextSteps": {
    "execute": true,
    "scheduledFor": "2024-01-15T12:45:00.000Z",
    "notifyCustomer": true
  }
}
42. 📊 Statistiques Globales (Admin)
Endpoint: GET /admin/statistics

Description: Récupère les statistiques globales de la plateforme

Authentification: Token JWT avec rôle ADMIN requis

Query Parameters:

period (optionnel): day|week|month|quarter|year (default: month)

metrics (optionnel): Liste de métriques spécifiques

Response (200 OK):

json
{
  "period": {
    "from": "2024-01-01",
    "to": "2024-01-15"
  },
  "users": {
    "total": 150,
    "active": 142,
    "new": 15,
    "growth": 10.0,
    "retention": 95.3
  },
  "accounts": {
    "total": 320,
    "averagePerUser": 2.13,
    "byType": {
      "CHECKING": 150,
      "SAVINGS": 120,
      "BUSINESS": 50
    }
  },
  "transactions": {
    "total": 12500,
    "totalVolume": 1250000.75,
    "averageAmount": 100.00,
    "successRate": 99.8,
    "byType": {
      "TRANSFER": 8000,
      "DEPOSIT": 3000,
      "WITHDRAWAL": 1000,
      "PAYMENT": 500
    }
  },
  "financials": {
    "totalAssets": 2500000.00,
    "dailyVolume": 125000.50,
    "feesCollected": 12500.75,
    "averageBalance": 7812.50
  },
  "performance": {
    "apiResponseTime": 120.5,
    "databaseLatency": 15.2,
    "uptime": 99.99,
    "peakConcurrentUsers": 45
  },
  "alerts": {
    "total": 25,
    "resolved": 20,
    "falsePositives": 3,
    "averageResolutionTime": "30m"
  }
}


# 📈 Monitoring & Santé
43. 🩺 Health Check Détaillé
Endpoint: GET /actuator/health

Description: Vérification complète de la santé du système

Authentification: Aucune (public)

Response (200 OK):

json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "result": "1",
        "validationQuery": "SELECT 1"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 536870912000,
        "free": 429496729600,
        "threshold": 10485760,
        "exists": true
      }
    },
    "ping": {
      "status": "UP"
    },
    "redis": {
      "status": "UP",
      "details": {
        "version": "7.0.11"
      }
    },
    "mail": {
      "status": "UP",
      "details": {
        "location": "smtp.gmail.com:587"
      }
    },
    "externalServices": {
      "status": "UP",
      "components": {
        "fraudDetection": {
          "status": "UP",
          "latency": "45ms"
        },
        "paymentGateway": {
          "status": "UP",
          "latency": "120ms"
        },
        "smsGateway": {
          "status": "UP",
          "latency": "85ms"
        }
      }
    }
  }
}
44. 📊 Métriques Système
Endpoint: GET /actuator/metrics

Description: Récupère les métriques système détaillées

Authentification: Token JWT avec rôle ADMIN requis

Query Parameters:

tag (optionnel): Filtrer par tag spécifique

Response (200 OK):

json
{
  "names": [
    "jvm.memory.used",
    "jvm.memory.max",
    "jvm.gc.pause",
    "system.cpu.usage",
    "process.uptime",
    "http.server.requests",
    "cache.size",
    "database.connections.active"
  ],
  "availableTags": [
    {
      "tag": "uri",
      "values": ["/api/auth/login", "/api/accounts", "/api/transactions"]
    },
    {
      "tag": "method",
      "values": ["GET", "POST", "PUT", "DELETE"]
    },
    {
      "tag": "status",
      "values": ["200", "400", "401", "500"]
    }
  ]
}
45. 📈 Métriques Spécifiques
Endpoint: GET /actuator/metrics/{metricName}

Description: Récupère les détails d'une métrique spécifique

Authentification: Token JWT avec rôle ADMIN requis

Example: GET /actuator/metrics/http.server.requests

Response (200 OK):

json
{
  "name": "http.server.requests",
  "description": "Time spent serving HTTP requests",
  "baseUnit": "seconds",
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 12500
    },
    {
      "statistic": "TOTAL_TIME",
      "value": 1500.5
    },
    {
      "statistic": "MAX",
      "value": 0.85
    }
  ],
  "availableTags": [
    {
      "tag": "uri",
      "values": [
        "/api/auth/login",
        "/api/accounts",
        "/api/transactions/transfer"
      ]
    }
  ]
}
46. 📋 Informations Environnement
Endpoint: GET /actuator/env

Description: Récupère les variables d'environnement

Authentification: Token JWT avec rôle ADMIN requis

Response (200 OK):

json
{
  "activeProfiles": ["production"],
  "propertySources": [
    {
      "name": "server.ports",
      "properties": {
        "local.server.port": {
          "value": 8080
        }
      }
    },
    {
      "name": "applicationConfig: [classpath:/application.yml]",
      "properties": {
        "spring.datasource.url": {
          "value": "jdbc:postgresql://localhost:5432/financeapp"
        },
        "app.jwt.secret": {
          "value": "******"
        }
      }
    }
  ]
}
47. 🔧 Configuration
Endpoint: GET /actuator/configprops

Description: Récupère les propriétés de configuration

Authentification: Token JWT avec rôle ADMIN requis

Response (200 OK):

json
{
  "contexts": {
    "finance-app": {
      "beans": {
        "securityConfig": {
          "prefix": "app.security",
          "properties": {
            "jwt.secret": "******",
            "jwt.expiration": 86400000
          }
        },
        "dataSourceProperties": {
          "prefix": "spring.datasource",
          "properties": {
            "url": "jdbc:postgresql://localhost:5432/financeapp",
            "username": "postgres",
            "password": "******"
          }
        }
      }
    }
  }
}
48. 🚀 Info Build
Endpoint: GET /actuator/info

Description: Informations sur le build et la version

Authentification: Aucune (public)

Response (200 OK):

json
{
  "build": {
    "artifact": "finance-app",
    "name": "FinanceApp",
    "version": "1.0.0",
    "time": "2024-01-15T08:45:00.000Z",
    "group": "com.financeapp"
  },
  "git": {
    "branch": "main",
    "commit": {
      "id": "a1b2c3d4",
      "time": "2024-01-14T22:30:00.000Z"
    }
  },
  "java": {
    "version": "17.0.8",
    "vendor": {
      "name": "Eclipse Adoptium",
      "version": "Temurin-17.0.8+7"
    }
  }
}


# 🔒 Sécurité
49. 🛡️ Politiques de Sécurité
Endpoint: GET /security/policies

Description: Récupère les politiques de sécurité actives

Authentification: Token JWT requis

Response (200 OK):

json
{
  "password": {
    "minLength": 12,
    "requireUppercase": true,
    "requireLowercase": true,
    "requireNumbers": true,
    "requireSpecial": true,
    "maxAge": 90,
    "historySize": 5
  },
  "authentication": {
    "maxAttempts": 5,
    "lockDuration": "15m",
    "sessionTimeout": "30m",
    "requireMfa": false,
    "mfaMethods": ["TOTP", "SMS", "EMAIL"]
  },
  "transactions": {
    "dailyLimit": 1000.00,
    "monthlyLimit": 5000.00,
    "requireVerificationAbove": 500.00,
    "suspiciousAmount": 10000.00
  },
  "notifications": {
    "login": true,
    "transaction": true,
    "passwordChange": true,
    "newDevice": true
  }
}
50. 📱 Sessions Actives
Endpoint: GET /security/sessions

Description: Liste toutes les sessions actives de l'utilisateur

Authentification: Token JWT requis

Response (200 OK):

json
{
  "sessions": [
    {
      "id": "session123456",
      "createdAt": "2024-01-15T10:30:00.000Z",
      "lastUsed": "2024-01-15T12:00:00.000Z",
      "expiresAt": "2024-01-15T13:00:00.000Z",
      "device": {
        "type": "DESKTOP",
        "os": "Windows 11",
        "browser": "Chrome 120",
        "ip": "192.168.1.1",
        "location": "Paris, France",
        "isCurrent": true
      }
    },
    {
      "id": "session123457",
      "createdAt": "2024-01-14T15:45:00.000Z",
      "lastUsed": "2024-01-14T16:30:00.000Z",
      "expiresAt": "2024-01-14T17:15:00.000Z",
      "device": {
        "type": "MOBILE",
        "os": "iOS 17",
        "browser": "Safari",
        "ip": "89.156.234.123",
        "location": "Lyon, France",
        "isCurrent": false
      }
    }
  ],
  "summary": {
    "totalSessions": 2,
    "activeSessions": 1,
    "maxConcurrent": 3
  }
}
51. ❌ Révoquer Session
Endpoint: DELETE /security/sessions/{sessionId}

Description: Révoque une session spécifique

Authentification: Token JWT requis

Response (200 OK):

json
{
  "message": "Session révoquée",
  "sessionId": "session123457",
  "revokedAt": "2024-01-15T12:50:00.000Z",
  "remainingSessions": 1
}
52. ❌ Révoquer Toutes les Sessions
Endpoint: POST /security/sessions/revoke-all

Description: Révoque toutes les sessions sauf la courante

Authentification: Token JWT requis

Body:

json
{
  "reason": "Suspicion de sécurité",
  "notify": true
}
Response (200 OK):

json
{
  "message": "Toutes les sessions ont été révoquées",
  "sessionsRevoked": 1,
  "currentSessionPreserved": true,
  "notificationsSent": 1,
  "timestamp": "2024-01-15T12:55:00.000Z"
}
53. 📧 Journal d'Activité
Endpoint: GET /security/activity

Description: Récupère le journal d'activité de sécurité

Authentification: Token JWT requis

Query Parameters:

type (optionnel): LOGIN|LOGOUT|PASSWORD_CHANGE|TRANSACTION

status (optionnel): SUCCESS|FAILURE

fromDate (optionnel): Date de début

toDate (optionnel): Date de fin

Response (200 OK):

json
{
  "activities": [
    {
      "id": "activity123456",
      "type": "LOGIN",
      "timestamp": "2024-01-15T10:30:00.000Z",
      "status": "SUCCESS",
      "ip": "192.168.1.1",
      "location": "Paris, France",
      "device": "Chrome 120 on Windows 11",
      "details": {
        "method": "PASSWORD",
        "mfaUsed": false
      }
    },
    {
      "id": "activity123457",
      "type": "TRANSACTION",
      "timestamp": "2024-01-15T12:00:00.000Z",
      "status": "SUCCESS",
      "ip": "192.168.1.1",
      "details": {
        "transactionId": "770e8400-e29b-41d4-a716-446655440001",
        "amount": 150.75,
        "type": "TRANSFER"
      }
    }
  ],
  "summary": {
    "totalActivities": 42,
    "successful": 40,
    "failed": 2,
    "suspicious": 0
  }
}


# 🧪 Tests & Validation
54. 🧪 Test de Performance
Endpoint: GET /test/performance

Description: Test de performance de l'API

Authentification: Token JWT avec rôle ADMIN requis

Response (200 OK):

json
{
  "timestamp": "2024-01-15T13:00:00.000Z",
  "performance": {
    "database": {
      "connectionTime": "15ms",
      "queryTime": "45ms",
      "poolStatus": {
        "active": 3,
        "idle": 5,
        "waiting": 0
      }
    },
    "cache": {
      "hitRate": 0.92,
      "responseTime": "2ms"
    },
    "api": {
      "averageResponseTime": "120ms",
      "requestsPerSecond": 45,
      "errorRate": 0.02
    },
    "memory": {
      "used": "512MB",
      "max": "2GB",
      "free": "1.5GB"
    }
  },
  "recommendations": [
    "Database query optimization needed for /api/transactions",
    "Consider increasing cache size",
    "API response time within acceptable limits"
  ]
}
55. 🔍 Test de Validation
Endpoint: POST /test/validate

Description: Valide des données de test

Authentification: Token JWT avec rôle ADMIN requis

Headers:

Content-Type: application/json

Body:

json
{
  "type": "TRANSACTION",
  "data": {
    "fromAccountId": "660e8400-e29b-41d4-a716-446655440001",
    "toAccountId": "880e8400-e29b-41d4-a716-446655440001",
    "amount": 150.75,
    "description": "Test validation"
  }
}
Response (200 OK):

json
{
  "valid": true,
  "validationResults": [
    {
      "field": "fromAccountId",
      "valid": true,
      "message": "Compte source valide"
    },
    {
      "field": "toAccountId",
      "valid": true,
      "message": "Compte destination valide"
    },
    {
      "field": "amount",
      "valid": true,
      "message": "Montant valide"
    },
    {
      "field": "description",
      "valid": true,
      "message": "Description valide"
    }
  ],
  "businessRules": [
    {
      "rule": "DAILY_LIMIT",
      "passed": true,
      "details": {
        "remaining": 849.25,
        "limit": 1000.00
      }
    },
    {
      "rule": "BALANCE_SUFFICIENT",
      "passed": true,
      "details": {
        "currentBalance": 1250.50,
        "required": 150.75
      }
    },
    {
      "rule": "FRAUD_CHECK",
      "passed": true,
      "details": {
        "riskScore": 15,
        "threshold": 75
      }
    }
  ]
}
56. 📝 Test d'Intégration
Endpoint: POST /test/integration

Description: Test d'intégration des services externes

Authentification: Token JWT avec rôle ADMIN requis

Body:

json
{
  "services": ["FRAUD_DETECTION", "PAYMENT_GATEWAY", "SMS_GATEWAY"]
}
Response (200 OK):

json
{
  "timestamp": "2024-01-15T13:05:00.000Z",
  "results": [
    {
      "service": "FRAUD_DETECTION",
      "status": "UP",
      "responseTime": "45ms",
      "details": {
        "version": "2.1.0",
        "endpoint": "https://fraud.example.com/api/v2"
      }
    },
    {
      "service": "PAYMENT_GATEWAY",
      "status": "UP",
      "responseTime": "120ms",
      "details": {
        "provider": "Stripe",
        "mode": "LIVE",
        "balance": 12500.75
      }
    },
    {
      "service": "SMS_GATEWAY",
      "status": "UP",
      "responseTime": "85ms",
      "details": {
        "provider": "Twilio",
        "remainingCredits": 500
      }
    }
  ],
  "summary": {
    "total": 3,
    "up": 3,
    "down": 0,
    "averageResponseTime": "83ms"
  }
}


# 🔌 WebSocket & Événements Temps Réel
57. 🌐 Connexion WebSocket
Endpoint: ws://localhost:8080/api/ws

Description: Connexion WebSocket pour les événements temps réel

Authentification: Token JWT dans les query parameters

URL: ws://localhost:8080/api/ws?token={jwt_token}

Événements disponibles:

transaction.created

transaction.updated

transaction.completed

balance.updated

notification.new

security.alert

58. 📡 S'abonner aux Événements
Message (client → serveur):

json
{
  "type": "SUBSCRIBE",
  "channels": ["transactions", "notifications", "balances"]
}
Réponse (serveur → client):

json
{
  "type": "SUBSCRIBED",
  "channels": ["transactions", "notifications", "balances"],
  "timestamp": "2024-01-15T13:10:00.000Z"
}
59. 📨 Événement Transaction Créée
Message (serveur → client):

json
{
  "type": "transaction.created",
  "timestamp": "2024-01-15T13:15:00.000Z",
  "data": {
    "id": "770e8400-e29b-41d4-a716-446655440006",
    "reference": "TXN1705312345681",
    "amount": 75.50,
    "type": "TRANSFER",
    "status": "PENDING",
    "description": "Café du matin",
    "fromAccount": {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "accountNumber": "ACC1705312345678"
    },
    "toAccount": {
      "id": "880e8400-e29b-41d4-a716-446655440003",
      "accountNumber": "ACC1705318765433"
    }
  }
}
60. 💰 Événement Solde Mis à Jour
Message (serveur → client):

json
{
  "type": "balance.updated",
  "timestamp": "2024-01-15T13:15:05.000Z",
  "data": {
    "accountId": "660e8400-e29b-41d4-a716-446655440001",
    "accountNumber": "ACC1705312345678",
    "oldBalance": 1250.50,
    "newBalance": 1175.00,
    "change": -75.50,
    "transactionId": "770e8400-e29b-41d4-a716-446655440006"
  }
}
61. 🔔 Événement Notification
Message (serveur → client):

json
{
  "type": "notification.new",
  "timestamp": "2024-01-15T13:15:10.000Z",
  "data": {
    "id": "notif123456",
    "title": "Transaction effectuée",
    "message": "Vous avez effectué un paiement de 75.50€",
    "type": "TRANSACTION",
    "priority": "LOW",
    "read": false,
    "metadata": {
      "transactionId": "770e8400-e29b-41d4-a716-446655440006",
      "accountId": "660e8400-e29b-41d4-a716-446655440001"
    }
  }
}
62. 🛡️ Événement Sécurité
Message (serveur → client):

json
{
  "type": "security.alert",
  "timestamp": "2024-01-15T13:20:00.000Z",
  "data": {
    "id": "alert123457",
    "type": "SUSPICIOUS_LOGIN",
    "severity": "MEDIUM",
    "title": "Tentative de connexion depuis un nouvel appareil",
    "message": "Une tentative de connexion a été détectée depuis un nouvel appareil",
    "details": {
      "ip": "89.156.234.124",
      "location": "Berlin, Germany",
      "device": "Firefox 121 on Linux",
      "time": "2024-01-15T13:19:45.000Z"
    },
    "actions": ["VERIFY", "BLOCK", "IGNORE"]
  }
}


# 📚 Conclusion
Cette documentation couvre l'ensemble des fonctionnalités de l'API FinanceApp. Pour toute question supplémentaire ou pour signaler un problème, contactez l'équipe de support à support@financeapp.com.

# 🏷️ Tags d'API :
public : Endpoints accessibles sans authentification

authentication : Gestion de l'authentification et des utilisateurs

accounts : Gestion des comptes bancaires

transactions : Opérations financières

admin : Administration système (rôle ADMIN requis)

security : Sécurité et monitoring

websocket : Événements temps réel

test : Tests et validation

# 📈 Statistiques d'API :
Total Endpoints : 62 endpoints documentés

Moyenne Response Time : < 200ms

Disponibilité : 99.99% SLA

Limite de Requêtes : 1000 req/minute par utilisateur

Support : 24/7

# 🔗 Liens Utiles :
Documentation Interactive (Swagger)

Statut du Service

Guide d'Intégration

FAQ

Support Technique