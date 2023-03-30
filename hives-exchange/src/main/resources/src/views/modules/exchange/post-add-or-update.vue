<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="内容"></el-input>
    </el-form-item>
    <el-form-item label="创建日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建日期"></el-input>
    </el-form-item>
    <el-form-item label="修改日期" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="修改日期"></el-input>
    </el-form-item>
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="dataForm.nickname" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="浏览量" prop="visits">
      <el-input v-model="dataForm.visits" placeholder="浏览量"></el-input>
    </el-form-item>
    <el-form-item label="点赞数" prop="likes">
      <el-input v-model="dataForm.likes" placeholder="点赞数"></el-input>
    </el-form-item>
    <el-form-item label="回复数" prop="reply">
      <el-input v-model="dataForm.reply" placeholder="回复数"></el-input>
    </el-form-item>
    <el-form-item label="类型" prop="type">
      <el-input v-model="dataForm.type" placeholder="类型"></el-input>
    </el-form-item>
    <el-form-item label="是否显示[0-未被删除，1被删除]" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="是否显示[0-未被删除，1被删除]"></el-input>
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
          content: '',
          createTime: '',
          updateTime: '',
          userId: '',
          nickname: '',
          visits: '',
          likes: '',
          reply: '',
          type: '',
          isDeleted: ''
        },
        dataRule: {
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建日期不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '修改日期不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          visits: [
            { required: true, message: '浏览量不能为空', trigger: 'blur' }
          ],
          likes: [
            { required: true, message: '点赞数不能为空', trigger: 'blur' }
          ],
          reply: [
            { required: true, message: '回复数不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          isDeleted: [
            { required: true, message: '是否显示[0-未被删除，1被删除]不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/exchange/post/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.content = data.post.content
                this.dataForm.createTime = data.post.createTime
                this.dataForm.updateTime = data.post.updateTime
                this.dataForm.userId = data.post.userId
                this.dataForm.nickname = data.post.nickname
                this.dataForm.visits = data.post.visits
                this.dataForm.likes = data.post.likes
                this.dataForm.reply = data.post.reply
                this.dataForm.type = data.post.type
                this.dataForm.isDeleted = data.post.isDeleted
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
              url: this.$http.adornUrl(`/exchange/post/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'content': this.dataForm.content,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime,
                'userId': this.dataForm.userId,
                'nickname': this.dataForm.nickname,
                'visits': this.dataForm.visits,
                'likes': this.dataForm.likes,
                'reply': this.dataForm.reply,
                'type': this.dataForm.type,
                'isDeleted': this.dataForm.isDeleted
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
