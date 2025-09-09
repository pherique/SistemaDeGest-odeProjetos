CREATE DATABASE IF NOT EXISTS `project_management_db`;
USE `project_management_db`;

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `full_name` VARCHAR(255) NOT NULL,
    `cpf` VARCHAR(14) UNIQUE NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `role` ENUM('administrador', 'gerente', 'colaborador') NOT NULL,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL
);

-- Tabela de Projetos
CREATE TABLE IF NOT EXISTS `projects` (
    `project_id` INT AUTO_INCREMENT PRIMARY KEY,
    `project_name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `start_date` DATE NOT NULL,
    `due_date` DATE NOT NULL,
    `status` ENUM('planejado', 'em_andamento', 'concluido', 'cancelado') NOT NULL,
    `manager_id` INT,
    FOREIGN KEY (`manager_id`) REFERENCES `users`(`user_id`)
);

-- Tabela de Equipes
CREATE TABLE IF NOT EXISTS `teams` (
    `team_id` INT AUTO_INCREMENT PRIMARY KEY,
    `team_name` VARCHAR(255) NOT NULL,
    `description` TEXT
);

-- Tabela de relacionamento N:M entre teams e users (Membros da Equipe)
CREATE TABLE IF NOT EXISTS `team_members` (
    `team_member_id` INT AUTO_INCREMENT PRIMARY KEY,
    `team_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`team_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    UNIQUE (`team_id`, `user_id`) -- Garante que um usuário não seja adicionado duas vezes à mesma equipe
);

-- Tabela de relacionamento N:M entre projects e teams (Equipes do Projeto)
CREATE TABLE IF NOT EXISTS `project_teams` (
    `project_team_id` INT AUTO_INCREMENT PRIMARY KEY,
    `project_id` INT NOT NULL,
    `team_id` INT NOT NULL,
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`project_id`),
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`team_id`),
    UNIQUE (`project_id`, `team_id`) -- Garante que uma equipe não seja alocada duas vezes ao mesmo projeto
);

-- Tabela de Tarefas
CREATE TABLE IF NOT EXISTS `tasks` (
    `task_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `project_id` INT NOT NULL,
    `assigned_to_user_id` INT NOT NULL,
    `status` ENUM('pendente', 'em_execucao', 'concluida') NOT NULL,
    `planned_start_date` DATE NOT NULL,
    `planned_end_date` DATE NOT NULL,
    `actual_start_date` DATE,
    `actual_end_date` DATE,
    FOREIGN KEY (`project_id`) REFERENCES `projects`(`project_id`),
    FOREIGN KEY (`assigned_to_user_id`) REFERENCES `users`(`user_id`)
);

-- Inserção de dados de teste

-- Usuários de teste
INSERT INTO `users` (`full_name`, `cpf`, `email`, `role`, `username`, `password`)
VALUES
('Administrador Geral', '111.111.111-11', 'admin@example.com', 'administrador', 'admin', 'admin123'),
('Gerente de Projetos A', '222.222.222-22', 'gerenteA@example.com', 'gerente', 'gerenteA', 'gerente123'),
('Colaborador Um', '333.333.333-33', 'colaborador1@example.com', 'colaborador', 'colaborador1', 'colab123'),
('Colaborador Dois', '444.444.444-44', 'colaborador2@example.com', 'colaborador', 'colaborador2@example.com', 'colab456');

-- Projetos de teste
INSERT INTO `projects` (`project_name`, `description`, `start_date`, `due_date`, `status`, `manager_id`)
VALUES
('Sistema de Gestão Interna', 'Desenvolvimento de um sistema para gerenciar processos internos da empresa.', '2024-09-01', '2025-03-31', 'em_andamento', (SELECT user_id FROM users WHERE username = 'gerenteA')),
('Aplicativo Mobile para Clientes', 'Criação de um aplicativo móvel para interação com clientes.', '2024-10-15', '2025-06-30', 'planejado', (SELECT user_id FROM users WHERE username = 'gerenteA'));

-- Equipes de teste
INSERT INTO `teams` (`team_name`, `description`)
VALUES
('Equipe Alpha', 'Equipe responsável por desenvolvimento backend.'),
('Equipe Beta', 'Equipe responsável por desenvolvimento frontend e UI/UX.');

-- Membros da Equipe de teste
INSERT INTO `team_members` (`team_id`, `user_id`)
VALUES
((SELECT team_id FROM teams WHERE team_name = 'Equipe Alpha'), (SELECT user_id FROM users WHERE username = 'colaborador1')),
((SELECT team_id FROM teams WHERE team_name = 'Equipe Alpha'), (SELECT user_id FROM users WHERE username = 'gerenteA')),
((SELECT team_id FROM teams WHERE team_name = 'Equipe Beta'), (SELECT user_id FROM users WHERE username = 'colaborador2'));

-- Alocação de Equipes a Projetos de teste
INSERT INTO `project_teams` (`project_id`, `team_id`)
VALUES
((SELECT project_id FROM projects WHERE project_name = 'Sistema de Gestão Interna'), (SELECT team_id FROM teams WHERE team_name = 'Equipe Alpha')),
((SELECT project_id FROM projects WHERE project_name = 'Sistema de Gestão Interna'), (SELECT team_id FROM teams WHERE team_name = 'Equipe Beta'));

-- Tarefas de teste
INSERT INTO `tasks` (`title`, `description`, `project_id`, `assigned_to_user_id`, `status`, `planned_start_date`, `planned_end_date`, `actual_start_date`, `actual_end_date`)
VALUES
('Desenvolver módulo de autenticação', 'Implementar a tela de login e a lógica de validação de credenciais.', (SELECT project_id FROM projects WHERE project_name = 'Sistema de Gestão Interna'), (SELECT user_id FROM users WHERE username = 'colaborador1'), 'em_execucao', '2024-09-05', '2024-09-20', '2024-09-05', NULL),
('Criar protótipos de UI', 'Desenhar as telas principais do aplicativo mobile.', (SELECT project_id FROM projects WHERE project_name = 'Aplicativo Mobile para Clientes'), (SELECT user_id FROM users WHERE username = 'colaborador2'), 'pendente', '2024-10-20', '2024-11-10', NULL, NULL),
('Configurar banco de dados', 'Criar o schema do banco de dados e as tabelas iniciais.', (SELECT project_id FROM projects WHERE project_name = 'Sistema de Gestão Interna'), (SELECT user_id FROM users WHERE username = 'gerenteA'), 'concluida', '2024-09-01', '2024-09-04', '2024-09-01', '2024-09-03');


