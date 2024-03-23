package ru.gb.lesson3;

import java.sql.*;

public class JDBC {
  public static Connection conn;
  public static Statement statmt;
  public static ResultSet resSet;

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite")) {
      acceptConnection(connection);
      System.out.println("База Подключена!");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    //Conn();
    //CreateDB();
    //WriteDB();
    //ReadDB();
    //CloseDB();
  }


  // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
  public static void Conn() throws ClassNotFoundException, SQLException
  {
    conn = null;
    //Class.forName("org.sqlite.JDBC");
    conn = DriverManager.getConnection("jdbc:sqlite:test.sqlite");
    System.out.println("База Подключена!");
  }

  // --------Создание таблицы--------
  public static void CreateDB() throws ClassNotFoundException, SQLException
  {
    statmt = conn.createStatement();
    statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");

    System.out.println("Таблица создана или уже существует.");
  }

  // --------Заполнение таблицы--------
  public static void WriteDB() throws SQLException
  {
    statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
    statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
    statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");

    System.out.println("Таблица заполнена");
  }

  // --------Закрытие--------
  public static void CloseDB() throws ClassNotFoundException, SQLException
  {
    conn.close();
    statmt.close();
    resSet.close();

    System.out.println("Соединения закрыты");
  }


  // -------- Вывод таблицы--------
  public static void ReadDB() throws ClassNotFoundException, SQLException
  {
    resSet = statmt.executeQuery("SELECT * FROM users");

    while(resSet.next())
    {
      int id = resSet.getInt("id");
      String  name = resSet.getString("name");
      String  phone = resSet.getString("phone");
      System.out.println( "ID = " + id );
      System.out.println( "name = " + name );
      System.out.println( "phone = " + phone );
      System.out.println();
    }

    System.out.println("Таблица выведена");
  }

  private static void acceptConnection(Connection connection) throws SQLException {
    createTable(connection);
    insertData(connection);
    deleteRandomRow(connection,"Igor","Igor");

    updateRow(connection, "Igor", "Igor");

    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery("select id, name, second_name from person");

      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String secondName = resultSet.getString("second_name");

        System.out.println("id = " + id + ", name = " + name + ", second_name = " + secondName);
      }
    }
  }

  private static void insertData(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
        int affectedRows = statement.executeUpdate("""
        insert into person(id, name) values
        (1, 'Igor'), 
        (2, 'Person #2'), 
        (3, 'John'), 
        (4, 'Alex'), 
        (5, 'Peter') 
        """);

      System.out.println("INSERT: affected rows: " + affectedRows);
    }
  }

  private static void updateRow(Connection connection, String name, String secondName) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement("update person set second_name = $1 where name = $2")) {
      stmt.setString(1, secondName);
      stmt.setString(2, name);

      stmt.executeUpdate();
    }

//    try (Statement statement = connection.createStatement()) {
//      statement.executeUpdate("update person set secondName = " + secondName + "where name = " + name);
//    }
  }

  private static void deleteRandomRow(Connection connection, String name, String secondName) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement("update person set second_name = $1 where name = $2")) {
      stmt.setString(1, secondName);
      stmt.setString(2, name);
      stmt.executeUpdate();
    }
  }

  private static void deleteRow(Connection connection, int id) throws SQLException {
      try (PreparedStatement stmt = connection.prepareStatement("delete from person where id = $1")) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
      }
  }

  private static void createTable(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
      create table person (
        id bigint,
        name varchar(256),
        second_name varchar(256)
      )
      """);
    }
  }

}
