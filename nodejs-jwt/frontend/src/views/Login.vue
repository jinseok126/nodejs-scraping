<template>
    <v-container
    class="fill-height"
    fluid
    >
        <v-row
            align="center"
            justify="center"
        >
            <v-col
            cols="12"
            sm="8"
            md="4"
            >
            <v-card class="elevation-12">
                <v-toolbar
                color="primary"
                dark
                flat
                >
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                    <v-btn
                        href="/join"
                        color="primary"
                    >
                        JOIN
                    </v-btn>
                    </template>
                    <span>Codepen</span>
                </v-tooltip>
                </v-toolbar>
                <v-card-text>
                <v-form>
                    <v-text-field
                    label="Login"
                    name="login"
                    prepend-icon="person"
                    type="text"
                    v-model="userId"
                    ></v-text-field>

                    <v-text-field
                    id="password"
                    label="Password"
                    name="password"
                    prepend-icon="lock"
                    type="password"
                    v-model="userPw"
                    ></v-text-field>
                </v-form>
                </v-card-text>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="login">Login</v-btn>
                </v-card-actions>
            </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        userId: "",
        userPw: ""
    }),
    methods: {
        login() {
            axios.post('/user/loginCheck', {
                userId: this.userId,
                userPw: this.userPw
            }).then((result) => {
                const resultData = result.data;
                if(resultData.check === 1) {
                    // localstorage에 토큰 저장 후 메인 화면으로 이동
                    localStorage.setItem('token', resultData.token);
                    location.href="/";
                }
            })
        } // login
    } // methods
}
</script>
