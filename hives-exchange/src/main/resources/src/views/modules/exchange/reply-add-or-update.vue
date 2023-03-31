<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="贴子ID" prop="postId">
      <el-input v-model="dataForm.postId" placeholder="贴子ID"></el-input>
    </el-form-item>
    <el-form-item label="被回复的回复id" prop="replyId">
      <el-input v-model="dataForm.replyId" placeholder="被回复的回复id"></el-input>
    </el-form-item>
    <el-form-item label="回复内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="回复内容"></el-input>
    </el-form-item>
    <el-form-item label="创建日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建日期"></el-input>
    </el-form-item>
    <el-form-item label="回复人的用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="回复人的用户id"></el-input>
    </el-form-item>
    <el-form-item label="被回复人的用户id" prop="targetId">
      <el-input v-model="dataForm.targetId" placeholder="被回复人的用户id"></el-input>
    </el-form-item>
    <el-form-item label="回复人的昵称" prop="userNickname">
      <el-input v-model="dataForm.userNickname" placeholder="回复人的昵称"></el-input>
    </el-form-item>
    <el-form-item label="被回复人的昵称" prop="targetNickname">
      <el-input v-model="dataForm.targetNickname" placeholder="被回复人的昵称"></el-input>
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
          postId: '',
          replyId: '',
          content: '',
          createTime: '',
          userId: '',
          targetId: '',
          userNickname: '',
          targetNickname: '',
          isDeleted: ''
        },
        dataRule: {
          postId: [
            { required: true, message: '贴子ID不能为空', trigger: 'blur' }
          ],
          replyId: [
            { required: true, message: '被回复的回复id不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '回复内容不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建日期不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '回复人的用户id不能为空', trigger: 'blur' }
          ],
          targetId: [
            { required: true, message: '被回复人的用户id不能为空', trigger: 'blur' }
          ],
          userNickname: [
            { required: true, message: '回复人的昵称不能为空', trigger: 'blur' }
          ],
          targetNickname: [
            { required: true, message: '被回复人的昵称不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/exchange/reply/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.postId = data.reply.postId
                this.dataForm.replyId = data.reply.replyId
                this.dataForm.content = data.reply.content
                this.dataForm.createTime = data.reply.createTime
                this.dataForm.userId = data.reply.userId
                this.dataForm.targetId = data.reply.targetId
                this.dataForm.userNickname = data.reply.userNickname
                this.dataForm.targetNickname = data.reply.targetNickname
                this.dataForm.isDeleted = data.reply.isDeleted
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
              url: this.$http.adornUrl(`/exchange/reply/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'postId': this.dataForm.postId,
                'replyId': this.dataForm.replyId,
                'content': this.dataForm.content,
                'createTime': this.dataForm.createTime,
                'userId': this.dataForm.userId,
                'targetId': this.dataForm.targetId,
                'userNickname': this.dataForm.userNickname,
                'targetNickname': this.dataForm.targetNickname,
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
