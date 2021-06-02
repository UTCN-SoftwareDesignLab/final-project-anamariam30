<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-form>
          <v-text-field v-model="message.messageContent" label="Message" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Send </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "MessageDialog",
  props: {
    message: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      this.$emit("sendMsg");
    },
  },
};
</script>

<style scoped></style>
