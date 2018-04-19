// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,

    // Initialize Firebase
    firebase: {
        apiKey: '[AIzaSyBb6zi8khOPOkEGrv1rd3UCUHmjo8SKRdo]',
        authDomain: '[vault-d6c06.firebaseapp.com]',
        databaseURL: '[https://vault-d6c06.firebaseio.com]',
        projectId: '[vault-d6c06]',
        storageBucket: '[vault-d6c06.appspot.com]',
        messagingSenderId: '[128417817369]'
    }
};
