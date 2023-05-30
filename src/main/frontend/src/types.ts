export type userData ={
    user_id?: number;
    username: String;
    password: String;
    email?: String;
    role?: String;
  }

  export type taskData ={
    id:number,
    name: String,
    description: String,
    user: userData,
  }