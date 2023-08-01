<script lang="ts">
  import { getContext } from 'svelte';
  import Icon from '@components/Icon.svelte';
  import type Theme from '@types/Theme';

  const { colors }: Theme = getContext('Theme');
  const AUTHOR_FOOTER_BEFORE_TEXT = 'made with';
  const AUTHOR_FOOTER_AFTER_TEXT = 'by me';
</script>

<footer
  class="py-1 absolute inset-x-0 lg:right-[30%] bottom-0 flex justify-center bg-primary"
>
  <p class="text-white text-sm font-mono">
    {AUTHOR_FOOTER_BEFORE_TEXT}
    <Icon name="coffee" size={16} class="mx-1" color={colors['on-primary']} />
    {AUTHOR_FOOTER_AFTER_TEXT}
  </p>
</footer>
