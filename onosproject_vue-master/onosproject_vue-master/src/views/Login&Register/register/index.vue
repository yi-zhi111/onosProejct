<template>
  <div class="register-container">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="title">注册</h3>
      </div>
      <!--   name   -->
      <el-form-item prop="username">
        <span class="svg-container">
          <span class="el-icon-user" />
        </span>
        <el-input v-model="registerForm.username" class="registerItem" placeholder="用户名" />
      </el-form-item>
      <!--   birth   -->
      <el-form-item prop="birth" required>
        <span class="svg-container">
          <span class="el-icon-date" />
        </span>
        <el-date-picker v-model="registerForm.birth" type="date" placeholder="生日日期" value-format="yyyy-MM-dd" format="yyyy-MM-dd" />
      </el-form-item>
      <!--   password   -->
      <el-form-item prop="password">
        <span class="svg-container">
          <span class="el-icon-lock" />
        </span>
        <el-input
          :key="passwordType1"
          ref="password"
          v-model="registerForm.password"
          :type="passwordType1"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleRegister"
        />
        <span class="show-pwd" @click="showPwd1">
          <svg-icon :icon-class="passwordType1 === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <!--   checkPass   -->
      <el-form-item prop="checkPass">
        <span class="svg-container">
          <span class="el-icon-unlock" />
        </span>
        <el-input
          :key="passwordType2"
          ref="password"
          v-model="registerForm.checkPass"
          :type="passwordType2"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd2">
          <svg-icon :icon-class="passwordType2 === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <!--   tel   -->
      <el-form-item prop="tel">
        <span class="svg-container">
          <span class="el-icon-mobile-phone" />
        </span>
        <el-input v-model="registerForm.tel" placeholder="手机号码" class="registerItem" />
      </el-form-item>
      <!--   email   -->
      <el-form-item prop="email">
        <span class="svg-container">
          <span class="el-icon-paperclip" />
        </span>
        <el-input v-model="registerForm.email" placeholder="邮箱" class="registerItem" />
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleRegister">注册</el-button>

    </el-form>
  </div>
</template>

<script>

export default {
  name: 'Register',
  data() {
    // 人名不能为空
    const checkName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('姓名不能为空'))
      } else {
        callback()
      }
    }
    // 密码验证
    const checkPass1 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.registerForm.password !== '') {
          this.$refs.registerForm.validateField('checkPass')
        }
        callback()
      }
    }
    const checkPass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 手机号不能为空
    const checkPhone = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('手机号码不能为空'))
      } else {
        callback()
      }
    }
    // 验证邮箱
    const checkEmail = (rule, value, callback) => {
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
      if (regEmail.test(value)) {
        // 合法邮箱
        return callback()
      }
      callback(new Error('请输入合法的邮箱'))
    }
    return {
      registerForm: {
        id: '',
        username: '',
        birth: '',
        password: '',
        address: '',
        checkPass: '',
        tel: '',
        email: ''
      },
      registerRules: {
        username: [{ required: true, trigger: 'blur', validator: checkName }],
        password: [{ required: true, trigger: 'blur', validator: checkPass1 }],
        checkPass: [{ require: true, trigger: 'blur', validator: checkPass2 }],
        tel: [{ require: true, trigger: 'blur', validator: checkPhone }],
        email: [{ require: true, trigger: 'blur', validator: checkEmail }]
      },
      loading: false,
      passwordType1: 'password',
      passwordType2: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd1() {
      if (this.passwordType1 === 'password') {
        this.passwordType1 = ''
      } else {
        this.passwordType1 = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    showPwd2() {
      if (this.passwordType2 === 'password') {
        this.passwordType2 = ''
      } else {
        this.passwordType2 = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/register', this.registerForm).then(() => {
            this.$message({
              message: '注册成功',
              type: 'success',
              duration: 1500
            })
            this.$router.push({ path: this.redirect || '/login' })
            this.loading = false
          }).catch(() => {
            // 注册失败
            this.$message({
              message: '注册失败',
              type: 'error',
              duration: 1500
            })
            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .register-container .el-input input {
    color: $cursor;
  }
}
/* reset element-ui css */

.register-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

::v-deep .el-input__prefix {
  display: none;
}

.register-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .register-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 100px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  .title-container {
    display: flex;
    margin-bottom: 30px;
    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0 auto 0 auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
