<script lang="ts">
    import {
        EMAIL,
        PERSONAL_GITHUB_URL,
        PERSONAL_LINKEDIN_URL,
    } from './contact-info';
    import CaptionOnHover from '@components/CaptionOnHover.svelte';
    import Icon from '@components/Icon.svelte';
    import type Theme from '@domain/Theme';
    import { getContext } from 'svelte';

    const { colors }: Theme = getContext('Theme');

    const GITHUB_LABEL = 'github';
    const LINKEDIN_LABEL = 'linkedin';
    const EMAIL_LABEL = 'email';
    const EMAIL_CAPTION = 'Copy to clipboard';

    function copyToClipboard(text: string) {
        navigator.clipboard.writeText(text);
    }
</script>

<section class="mt-24 lg:mt-0">
    <ul class="mt-7 flex flex-row justify-center gap-4">
        <li>
            <a
                href={PERSONAL_LINKEDIN_URL}
                target="_blank"
                rel="noopener noreferrer"
            >
                <Icon
                    name="linkedin"
                    color={colors['primary-variant']}
                    label={LINKEDIN_LABEL}
                />
            </a>
        </li>
        <li>
            <a
                href={PERSONAL_GITHUB_URL}
                target="_blank"
                rel="noopener noreferrer"
            >
                <Icon
                    name="github"
                    color={colors['primary-variant']}
                    label={GITHUB_LABEL}
                />
            </a>
        </li>
        <li>
            <button on:click={() => copyToClipboard(EMAIL)}
                class="hover:cursor-grab show-clipboard-copy">
                <CaptionOnHover caption={EMAIL_CAPTION}>
                    <Icon
                        name="gmail"
                        color={colors['primary-variant']}
                        label={EMAIL}
                    />
                </CaptionOnHover>
            </button>
        </li>
    </ul>
</section>
