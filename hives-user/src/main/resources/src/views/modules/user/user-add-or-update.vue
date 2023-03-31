<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="手机号码" prop="phoneNumber">
      <el-input v-model="dataForm.phoneNumber" placeholder="手机号码"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="dataForm.password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="会员等级" prop="level">
      <el-input v-model="dataForm.level" placeholder="会员等级"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="dataForm.nickname" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="性别"></el-input>
    </el-form-item>
    <el-form-item label="头像" prop="header">
      <el-input v-model="dataForm.header" placeholder="头像"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="生日" prop="birthday">
      <el-input v-model="dataForm.birthday" placeholder="生日"></el-input>
    </el-form-item>
    <el-form-item label="注册日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="注册日期"></el-input>
    </el-form-item>
    <el-form-item label="最后活跃" prop="lastTime">
      <el-input v-model="dataForm.lastTime" placeholder="最后活跃"></el-input>
    </el-form-item>
    <el-form-item label="粉丝数" prop="fansCount">
      <el-input v-model="dataForm.fansCount" placeholder="粉丝数"></el-input>
    </el-form-item>
    <el-form-item label="关注数" prop="followCount">
      <el-input v-model="dataForm.followCount" placeholder="关注数"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          username: '',
          phoneNumber: '',
          password: '',
          level: '',
          nickname: '',
          gender: '',
          header: '',
          email: '',
          birthday: '',
          createTime: '',
          lastTime: '',
          fansCount: '',
          followCount: ''
        },
        dataRule: {
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          phoneNumber: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          level: [
            { required: true, message: '会员等级不能为空', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          header: [
            { required: true, message: '头像不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          birthday: [
            { required: true, message: '生日不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '注册日期不能为空', trigger: 'blur' }
          ],
          lastTime: [
            { required: true, message: '最后活跃不能为空', trigger: 'blur' }
          ],
          fansCount: [
            { required: true, message: '粉丝数不能为空', trigger: 'blur' }
          ],
          followCount: [
            { required: true, message: '关注数不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/user/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.username = data.user.username
                this.dataForm.phoneNumber = data.user.phoneNumber
                this.dataForm.password = data.user.password
                this.dataForm.level = data.user.level
                this.dataForm.nickname = data.user.nickname
                this.dataForm.gender = data.user.gender
                this.dataForm.header = data.user.header
                this.dataForm.email = data.user.email
                this.dataForm.birthday = data.user.birthday
                this.dataForm.createTime = data.user.createTime
                this.dataForm.lastTime = data.user.lastTime
                this.dataForm.fansCount = data.user.fansCount
                this.dataForm.followCount = data.user.followCount
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/user/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'username': this.dataForm.username,
                'phoneNumber': this.dataForm.phoneNumber,
                'password': this.dataForm.password,
                'level': this.dataForm.level,
                'nickname': this.dataForm.nickname,
                'gender': this.dataForm.gender,
                'header': this.dataForm.header,
                'email': this.dataForm.email,
                'birthday': this.dataForm.birthday,
                'createTime': this.dataForm.createTime,
                'lastTime': this.dataForm.lastTime,
                'fansCount': this.dataForm.fansCount,
                'followCount': this.dataForm.followCount
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
