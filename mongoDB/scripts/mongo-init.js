// This comment is even less important than the other one
db.createUser(
    {
        user: 'root',
        pwd: 'mongo',
        roles: [
            {
                role: 'dbOwner',
                db: 'coindashboard',
            },
        ],
    }
);

db.auth('root', 'mongo')
