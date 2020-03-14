import Sequelize from 'sequelize';

export default (sequelize, DataTypes) =>
  sequelize.define('User', {
    id: {
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    username: {
      type: Sequelize.STRING,
      unique: true,
    },
    name: Sequelize.STRING,
    surname: Sequelize.STRING,
    password: Sequelize.STRING,
    Picture: Sequelize.STRING,
    Description: Sequelize.STRING,
    DateOfRegistration: Sequelize.DATE,
  });
