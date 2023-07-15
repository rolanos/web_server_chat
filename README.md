# web_server_chat
  web_server_chat - веб-серверная часть для учебного приложения "_Android чат на Java_".

## Documentation
  1. Архитектура проекта

Структура проекта:
- База данных(PostgreSQL 14)
- Веб-сервер(Apache Tomcat 10)
- Клиентское приложение(Java)
Запросы отправляются с клиентского приложения на веб-сервер, который обрабатывает полученный на веб-сервере, который обрабатывает полученный запрос и возвращает ответ в виде структуры JSON (см. рис. 1).

![Alt text](image.png)\
Рисунок 1 – Диаграмма развертывания проекта

---
2. База данных

База данных реализована на технологии PostgreSQL. PostgreSQL - это объектно-реляционная система управления базами данных (СУБД), которая является мощным инструментом для хранения, организации и обработки больших объемов данных. База данных будет иметь название «chatdb».
 В данном проекте база данных включает в себя таблицы: Пользователи (см. рис. 2.1), Сообщения(см. рис. 2.2), Чаты(см. рис. 2.3).

![Alt text](image-1.png)\
Рисунки 2

2.2. Описание таблиц базы данных
1. Таблица users:
- Поле id  – уникальный идентификатор пользователя.
- Поле name – имя пользователя.
2. Таблица messages:\
-Поле id – уникальный идентификатор сообщения.
-Поле owner_id – идентификатор отправителя сообщения.
-Поле chat_id – идентификатор чата.\
-Поле text – текст сообщения.\
-Поле user_name – имя отправителя сообщения.\
-Поле dispatch – дата отправки сообщения.
3. Таблица chats:\
-Поле id - идентификатор чата.\
-Поле name – название чата.\
-Поле participant_id – идентификатор пользователя в чате.


3. Сервера

3.1. Описание сервера.
Сервер реализован на технологии Apache Tomcat 10. Apache Tomcat – контейнер сервлетов(servlets), так же реализующий спецификацию JavaServer Pages(JSP). Tomcat позволяет запускать веб-приложения. В архитектуре проекта Apache Server играет связующую роль между клиентским приложением и сервером базы данных(PostgreSQL). Эта связь реализуется с помощью Java Database Connectivity(JDBC) - промышленный стандарт взаимодействия Java-приложений с различными СУБД. JDBC основан на концепции драйверов, позволяющих получать соединение с базой данных по специальному описанному URL.\

Клиентское приложение отправляет различные запросы на сервер Tomcat, который будучи соединенным с postgreSQL сервером через стандарт JDBC обрабатывает их и посылает структуру JSON в Android приложение клиента. В клиентском приложении JSON структура преобразуется в определенную модель данных и выводится на экран. Так же на серверной стороне реализован механизм WebSocket. WebSocket – протокол связи поверх TCP-соединения, предназначенный для обмена сообщениями между Tomcat сервером и Andoid приложением клиента. WebSocket клиентского приложения устанавливает соединение с WebSocket сервера регистрируя новую сессию пользователя. По этому соединению и клиент, и сервер прослушивают сообщения друг от друга. Когда клиент отправляет сообщение на сервер через механизм WebSocket, сервер сохраняет данное сообщение в базе данных(PostgreSQL) и рассылает всем подключенным сессиям.
На сервере Tomcat благодаря технологиям servlet и JSP так же реализована  серверная страница администрирования (см. рис. 3).

![Alt text](image-2.png)\
Рисунок - 3

4. мобильный клиент

4.1. Описание работы мобильного клиента
Мобильный клиент реализован на операционной системе Android в виде приложения, написанного на языке Java в среде Android Studio. 
При запуске отображается первый экран, на котором пользователь должен выбрать пользователя из списка всех пользователей (см. рис. 4.1). 

![Alt text](image-3.png)\
Рисунок - 4.1

После выбора происходит переход на следующий экран, где отобразятся все чаты, в которых состоит выбранный пользователь. На втором экране так же необходимо выбрать интересующий нас чат из списка (см. рис. 4.2).

После выбора произойдет переход на третий экран. При запуске третьего экрана автоматически выводится все содержимое чата: все сообщения других пользователей с именами их отправителей, сообщения выбранного ранее пользователя. Пользователь может отправить сообщение, используя строку ввода и кнопку отправки, находящиеся снизу, после чего сообщение через вебсокет отправится на сервер откуда будет разослано на все устройства, включая устройство пользователя, и будет выведено на экран. При желании выхода из чата пользователь может воспользоваться кнопкой возврата к списку чатов, расположенной в правом верхнем углу экрана (см. рис. 4.3).

![Alt text](image-4.png)\
Рисунок 4.3 - Чат

