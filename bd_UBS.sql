CREATE DATABASE bd_UBS;
USE bd_UBS;

CREATE TABLE Funcionario (
  idFuncionario INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NULL,
  cpf VARCHAR(12) NULL,
  senha VARCHAR(8) NULL,
  cargo INTEGER UNSIGNED NULL,
  PRIMARY KEY(idFuncionario)
);

CREATE TABLE Paciente (
  cpf VARCHAR(12) NOT NULL,
  nome VARCHAR(45) NULL,
  email VARCHAR(50) NULL,
  data_nasc DATE NULL,
  celular varchar(20) NULL,
  endereco VARCHAR(45) NULL,
  cidade VARCHAR(20) NULL,
  cep VARCHAR(20) NULL,
  nr_SUS INTEGER UNSIGNED NULL,
  genero VARCHAR(2) NULL,
  PRIMARY KEY(cpf)
);

CREATE TABLE Especialidade (
  idEspecialidade INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome INTEGER UNSIGNED NULL,
  PRIMARY KEY(idEspecialidade)
);

CREATE TABLE Atendimento (
  idAtendimento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Especialidade_idEspecialidade INTEGER UNSIGNED NOT NULL,
  Paciente_cpf VARCHAR(12) NOT NULL,
  data_atendimento DATE NULL,
  tipo INTEGER UNSIGNED NULL,
  nr_atendimento INTEGER UNSIGNED NULL,
  pressao_art FLOAT NULL,
  temperatura FLOAT NULL,
  sintomas VARCHAR(45) NULL,
  frequencia_card FLOAT NULL,
  status_aten INTEGER UNSIGNED NULL,
  PRIMARY KEY(idAtendimento),
  INDEX Atendimento_FKIndex1(Paciente_cpf),
  INDEX Atendimento_FKIndex2(Especialidade_idEspecialidade),
  FOREIGN KEY(Paciente_cpf)
    REFERENCES Paciente(cpf)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Especialidade_idEspecialidade)
    REFERENCES Especialidade(idEspecialidade)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Prontuario (
  idProntuario INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Funcionario_idFuncionario INTEGER UNSIGNED NOT NULL,
  Atendimento_idAtendimento INTEGER UNSIGNED NOT NULL,
  anamnese TEXT NULL,
  plano_terapeutico TEXT NULL,
  encaminhamento VARCHAR(45) NULL,
  PRIMARY KEY(idProntuario),
  INDEX Prontuario_FKIndex1(Atendimento_idAtendimento),
  INDEX Prontuario_FKIndex2(Funcionario_idFuncionario),
  FOREIGN KEY(Atendimento_idAtendimento)
    REFERENCES Atendimento(idAtendimento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Funcionario_idFuncionario)
    REFERENCES Funcionario(idFuncionario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

INSERT INTO Funcionario VALUES (1,'João Medeiros','11111111111','12345678',3),
(2,'Ivete Sangalo','22222222222','12345678',3),
(3,'Tadeu Jhonis','44444444444','3353353',4),
(4,'Drauzio Varella','99999999999','3535332',4),
(5,'Drauzio Varella','99999999999','3535332',4);

INSERT INTO Especialidade VALUES (1,2),(2,3),(3,4),(4,4);

INSERT INTO Paciente VALUES ('12312312312','Jorge Magalhães','jorgemagalhães@gmail.com','2007-09-29','11973846783','Rua do Centro','São Paulo','19323210',35325332,'M'),
('92947282092','Pedro Marcos Pereira','pedro@gmail.com','2004-07-15','16929387502','Rua São Joaquim','São José dos Pinhais','056602920',53523535,'M'),
('02002002002','Jess','jessica@gmail.com','2004-10-15','11956273829','Rua Marechal José','Rio de Janeiro','05672920',443153543,'F'),
('01001001001','Natan Oliveira','natan@gmail.com','2003-07-20','11920382789','Rua Manoel José','São Paulo','05882028',53531535,'M'),
('55555555555','Pedro Vargas','pedrovargas@gmail.com','2005-09-23','11994346783','Rua Joaozin do Sul','São Paulo','14643210',35325332,'M');

INSERT INTO Atendimento VALUES (1,1,'55555555555','2023-05-29',2,34,43,36.5,'Dor',23.5,3),
(3,1,'01001001001','2023-05-27',2,34,43,36.5,'Dor',23.5,3),
(4,2,'02002002002','2023-05-26',4,39,44,35.5,'Dores Musculares',23.5,4),
(5,2,'38515000830','2023-04-26',4,39,44,35.5,'Sentindo náuseas',23.5,4),
(6,3,'12312312312','2023-05-31',4,39,44,35.5,'Passando mal',23.5,4),
(7,4,'12312312312','2023-05-31',4,39,44,35.5,'Passando mal',23.5,4);


