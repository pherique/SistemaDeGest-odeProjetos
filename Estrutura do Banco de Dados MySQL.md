
## Estrutura do Banco de Dados MySQL

### Banco de Dados
`project_management_db`

### Tabelas

#### 1. `users`
- `user_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `full_name` (VARCHAR(255), NOT NULL)
- `cpf` (VARCHAR(14), UNIQUE, NOT NULL)
- `email` (VARCHAR(255), UNIQUE, NOT NULL)
- `role` (VARCHAR(50), NOT NULL) -- administrador, gerente, colaborador
- `username` (VARCHAR(50), UNIQUE, NOT NULL)
- `password` (VARCHAR(255), NOT NULL)

#### 2. `projects`
- `project_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `project_name` (VARCHAR(255), NOT NULL)
- `description` (TEXT)
- `start_date` (DATE, NOT NULL)
- `due_date` (DATE, NOT NULL)
- `status` (VARCHAR(50), NOT NULL) -- planejado, em andamento, concluído, cancelado
- `manager_id` (INT, FOREIGN KEY REFERENCES `users`(user_id))

#### 3. `teams`
- `team_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `team_name` (VARCHAR(255), NOT NULL)
- `description` (TEXT)

#### 4. `team_members` (Tabela de relacionamento N:M entre `teams` e `users`)
- `team_member_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `team_id` (INT, FOREIGN KEY REFERENCES `teams`(team_id))
- `user_id` (INT, FOREIGN KEY REFERENCES `users`(user_id))

#### 5. `project_teams` (Tabela de relacionamento N:M entre `projects` e `teams`)
- `project_team_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `project_id` (INT, FOREIGN KEY REFERENCES `projects`(project_id))
- `team_id` (INT, FOREIGN KEY REFERENCES `teams`(team_id))

#### 6. `tasks`
- `task_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `title` (VARCHAR(255), NOT NULL)
- `description` (TEXT)
- `project_id` (INT, FOREIGN KEY REFERENCES `projects`(project_id))
- `assigned_to_user_id` (INT, FOREIGN KEY REFERENCES `users`(user_id))
- `status` (VARCHAR(50), NOT NULL) -- pendente, em execução, concluída
- `planned_start_date` (DATE, NOT NULL)
- `planned_end_date` (DATE, NOT NULL)
- `actual_start_date` (DATE)
- `actual_end_date` (DATE)


